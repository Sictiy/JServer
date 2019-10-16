package com.sictiy.plugin.processor;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.sictiy.plugin.util.PsiAnnotationSearchUtil;

/**
 * @author sictiy.xu
 * @version 2019/10/15 18:28
 **/
public abstract class AbstractProcessor implements Processor
{
    private Class<? extends Annotation>[] supportedAnnotations;

    private Class<? extends PsiElement> supportedClass;

    @SafeVarargs
    public AbstractProcessor(Class<? extends PsiElement> supportedClass, Class<? extends Annotation>... supportedAnnotations)
    {
        this.supportedAnnotations = supportedAnnotations;
        this.supportedClass = supportedClass;
    }


    @NotNull
    @Override
    public Class<? extends Annotation>[] getSupportedAnnotations()
    {
        return supportedAnnotations;
    }

    @NotNull
    @Override
    public Class<? extends PsiElement> getSupportedClass()
    {
        return supportedClass;
    }

    @NotNull
    @Override
    public List<? super PsiElement> process(@NotNull PsiClass psiClass)
    {
        PsiAnnotation psiAnnotation = PsiAnnotationSearchUtil.findAnnotation(psiClass, getSupportedAnnotations());
        if (psiAnnotation != null && validate(psiClass, psiAnnotation))
        {
            return generatePsiElement(psiClass, psiAnnotation);
        }
        return Collections.emptyList();
    }

    /**
     * 生成psi元素
     *
     * @param psiClass      psiClass
     * @param psiAnnotation psiAnnotation
     * @return java.util.List<? super com.intellij.psi.PsiElement>
     **/
    @NotNull
    protected abstract List<? super PsiElement> generatePsiElement(@NotNull PsiClass psiClass, @NotNull PsiAnnotation psiAnnotation);

    /**
     * 验证类和注解
     *
     * @param psiClass      psiClass
     * @param psiAnnotation psiAnnotation
     * @return boolean
     **/
    protected abstract boolean validate(@NotNull PsiClass psiClass, @NotNull PsiAnnotation psiAnnotation);
}
