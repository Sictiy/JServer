package com.sictiy.plugin.processor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.sictiy.plugin.psi.LombokLightMethodBuilder;
import com.sictiy.plugin.util.PsiClassUtil;
import com.sictiy.plugin.util.PsiMethodUtil;
import com.sictiy.processor.single.SingleInstance;

/**
 * @author sictiy.xu
 * @version 2019/10/14 15:31
 **/
public class SingleInstanceProcessor extends AbstractProcessor
{
    public SingleInstanceProcessor()
    {
        super(PsiMethod.class, SingleInstance.class);
    }

    private static final String METHOD_NAME = "getInstance";

    @NotNull
    @Override
    protected List<? super PsiElement> generatePsiElement(@NotNull PsiClass psiClass, @NotNull PsiAnnotation psiAnnotation)
    {
        return Collections.singletonList(createOneGetInstanceMethod(psiClass, psiAnnotation));
    }

    @Override
    protected boolean validate(@NotNull PsiClass psiClass, @NotNull PsiAnnotation psiAnnotation)
    {
        if (!validateAnnotationOnRigthType(psiClass))
        {
            return false;
        }
        if (!validateExistingMethods(psiClass))
        {
            return false;
        }
        return true;
    }

    private boolean validateAnnotationOnRigthType(@NotNull PsiClass psiClass)
    {
        boolean result = true;
        if (psiClass.isAnnotationType() || psiClass.isInterface())
        {
            result = false;
        }
        return result;
    }

    private boolean validateExistingMethods(@NotNull PsiClass psiClass)
    {
        boolean result = true;

        final Collection<PsiMethod> classMethods = PsiClassUtil.collectClassMethodsIntern(psiClass);
        if (PsiMethodUtil.hasMethodByName(classMethods, METHOD_NAME))
        {
            result = false;
        }

        return result;
    }

    private PsiMethod createOneGetInstanceMethod(@NotNull PsiClass psiClass, @NotNull PsiAnnotation psiAnnotation)
    {
        final PsiManager psiManager = psiClass.getManager();

        final LombokLightMethodBuilder methodBuilder = new LombokLightMethodBuilder(psiManager, METHOD_NAME);
        methodBuilder.withMethodReturnType(PsiClassUtil.getTypeWithGenerics(psiClass));
        methodBuilder.withContainingClass(psiClass);
        methodBuilder.withNavigationElement(psiAnnotation);
        methodBuilder.withModifier(PsiModifier.PUBLIC);
        methodBuilder.withModifier(PsiModifier.STATIC);
        return methodBuilder;
    }
}
