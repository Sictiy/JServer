package com.sictiy.processor.single;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;

import static com.sun.tools.javac.util.List.nil;

/**
 * @author sictiy.xu
 * @version 2019/10/11 15:13
 **/
@SupportedAnnotationTypes("com.sictiy.processor.single.SingleInstance")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class SingleProcessor extends AbstractProcessor
{
    // 打印器
    private Messager messager;

    private TreeMaker treeMaker;

    private Names names;

    private JavacTrees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        this.messager = processingEnv.getMessager();     //用于编译时的输出
        this.trees = JavacTrees.instance(processingEnv); //AST 树
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);    //封装了定义方法、变量、类等等的方法
        this.names = Names.instance(context);            //用于创建标识符
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        var set = roundEnv.getElementsAnnotatedWith(SingleInstance.class);
        set.forEach(element -> {
            JCTree jcTree = trees.getTree(element);
            jcTree.accept(new TreeTranslator()
            {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl)
                {
                    createPrivateConstructor(jcClassDecl);
                    JCTree.JCClassDecl innerClass = createInnerClass(jcClassDecl);
                    createReturnInstance(jcClassDecl, innerClass);
                }
            });
        });
        return true;
    }

    private void createReturnInstance(JCTree.JCClassDecl jcClassDecl, JCTree.JCClassDecl innerClass)
    {
        // 方法是静态的公共的
        JCTree.JCModifiers fieldMod = treeMaker.Modifiers(Flags.PUBLIC | Flags.STATIC);
        // 返回类型 是 单例类的类型
        JCTree.JCIdent singletonClassType = treeMaker.Ident(jcClassDecl.name);
        // 构造return 语句块
        JCTree.JCBlock body = createReturnBlock(innerClass);
        //创建方法
        JCTree.JCMethodDecl methodDec = treeMaker.MethodDef(fieldMod,
                this.names.fromString("getInstance"),
                singletonClassType, nil(), nil(), nil(), body, null);

        jcClassDecl.defs = jcClassDecl.defs.prepend(methodDec);
    }

    private JCTree.JCBlock createReturnBlock(JCTree.JCClassDecl innerClass)
    {
        JCTree.JCIdent holderInnerClassType = treeMaker.Ident(innerClass.name);

        JCTree.JCFieldAccess instanceVarAccess = treeMaker.Select(holderInnerClassType, names.fromString("instance")); //获取内联类中的静态变量
        JCTree.JCReturn returnValue = treeMaker.Return(instanceVarAccess);//创建 return 语句

        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.append(returnValue);

        return treeMaker.Block(0L, statements.toList());
    }

    private JCTree.JCClassDecl createInnerClass(JCTree.JCClassDecl jcClassDecl)
    {
        // 创建内联类
        JCTree.JCClassDecl innerClass = treeMaker.ClassDef(
                treeMaker.Modifiers(Flags.PRIVATE | Flags.STATIC),
                names.fromString(jcClassDecl.name + "Holder"),  //类名
                nil(),                                           //泛型参数
                null,                                            //extending
                nil(),                                           //implementing
                nil()                                            //类定义的详细语句，包括字段，方法定义等
        );

        // 给内联类添加一个私有静态的单例的属性 instance
        // instance 的类型为原静态类
        JCTree.JCIdent singletonClassType = treeMaker.Ident(jcClassDecl.name); //获取注解的类型
        // instance 的赋值语句
        JCTree.JCNewClass newKeyword = treeMaker.NewClass(null,                 //encl,enclosingExpression lambda 箭头吗？不太清楚
                nil(),                //参数类型列表
                singletonClassType,   //待创建对象的类型
                nil(),                //参数类型
                null);                //类定义
        // instance 是私有，静态，final的
        JCTree.JCModifiers fieldMod = treeMaker.Modifiers(Flags.PRIVATE | Flags.STATIC | Flags.FINAL);
        // 定义这个instance变量
        JCTree.JCVariableDecl instanceVar = treeMaker.VarDef(
                fieldMod,                                                        //修饰符
                names.fromString("instance"),                                    //变量名
                singletonClassType,                                              //类型
                newKeyword);                                                     //赋值语句
        // 将instance 添加到内联函数中
        innerClass.defs = innerClass.defs.prepend(instanceVar);

        // 将内联类添加到原单例类中
        jcClassDecl.defs = jcClassDecl.defs.append(innerClass);
        return innerClass;
    }

    private void createPrivateConstructor(JCTree.JCClassDecl singletonClass)
    {
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PRIVATE);
        JCTree.JCBlock block = treeMaker.Block(0L, nil());
        JCTree.JCMethodDecl constructor = treeMaker
                .MethodDef(
                        modifiers,                   //修饰符
                        names.fromString("<init>"),  //函数名
                        null,                        //方法返回的类型
                        nil(),                       //泛型参数
                        nil(),                       //参数
                        nil(),                       //throw
                        block,                       //函数代码块，这里是空代码块
                        null);                       //默认值

        //去掉默认的构造函数
        ListBuffer<JCTree> out = new ListBuffer<>();
        for (JCTree tree : singletonClass.defs)
        {
            if (isPublicDefaultConstructor(tree))
            {//是否公有无参数的构造函数
                continue;
            }
            out.add(tree);
        }
        out.add(constructor);
        singletonClass.defs = out.toList();
    }

    private boolean isPublicDefaultConstructor(JCTree jcTree)
    {
        if (jcTree.getKind() == Tree.Kind.METHOD)
        {
            JCTree.JCMethodDecl jcMethodDecl = (JCTree.JCMethodDecl) jcTree;
            return isConstructor(jcMethodDecl)
                    && isNoArgsMethod(jcMethodDecl)
                    && isPublicMethod(jcMethodDecl);
        }
        return false;
    }

    private static boolean isConstructor(JCTree.JCMethodDecl jcMethodDecl)
    {
        String name = jcMethodDecl.name.toString();
        return "<init>".equals(name);
    }

    private static boolean isNoArgsMethod(JCTree.JCMethodDecl jcMethodDecl)
    {
        List<JCTree.JCVariableDecl> jcVariableDeclList = jcMethodDecl.getParameters();
        return jcVariableDeclList == null
                || jcVariableDeclList.size() == 0;
    }

    private boolean isPublicMethod(JCTree.JCMethodDecl jcMethodDecl)
    {
        JCTree.JCModifiers jcModifiers = jcMethodDecl.getModifiers();
        Set<Modifier> modifiers = jcModifiers.getFlags();
        return modifiers.contains(Modifier.PUBLIC);
    }
}
