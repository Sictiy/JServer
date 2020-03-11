package com.sictiy.plugin.extension;

import java.util.Arrays;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.intellij.ide.structureView.StructureViewExtension;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.java.JavaClassTreeElement;
import com.intellij.ide.structureView.impl.java.PsiFieldTreeElement;
import com.intellij.ide.structureView.impl.java.PsiMethodTreeElement;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.sictiy.plugin.psi.MyLightClassBuilder;
import com.sictiy.plugin.psi.MyLightFieldBuilder;
import com.sictiy.plugin.psi.MyLightMethodBuilder;

/**
 * @author sictiy.xu
 * @version 2020/03/10 15:13
 **/
public class MyStructureViewExtension implements StructureViewExtension
{
    @Override
    public Class<? extends PsiElement> getType()
    {
        return PsiClass.class;
    }

    /**
     * 将自定义元素添加的structure面板
     *
     * @param psiElement psiElement
     * @return com.intellij.ide.structureView.StructureViewTreeElement[]
     **/
    @Override
    public StructureViewTreeElement[] getChildren(PsiElement psiElement)
    {
        final PsiClass parentClass = (PsiClass) psiElement;
        final Stream<PsiMethodTreeElement> methods = Arrays.stream(parentClass.getMethods())
                .filter(MyLightMethodBuilder.class::isInstance)
                .map(psiMethod -> new PsiMethodTreeElement(psiMethod, false));
        final Stream<PsiFieldTreeElement> fields = Arrays.stream(parentClass.getFields())
                .filter(MyLightFieldBuilder.class::isInstance)
                .map(psiField -> new PsiFieldTreeElement(psiField, false));
        final Stream<JavaClassTreeElement> classes = Arrays.stream(parentClass.getInnerClasses())
                .filter(MyLightClassBuilder.class::isInstance)
                .map(psiClass -> new JavaClassTreeElement(psiClass, false));
        return Stream.concat(Stream.concat(fields, methods), classes).toArray(StructureViewTreeElement[]::new);
    }

    @Nullable
    @Override
    public Object getCurrentEditorElement(Editor editor, PsiElement psiElement)
    {
        return null;
    }
}
