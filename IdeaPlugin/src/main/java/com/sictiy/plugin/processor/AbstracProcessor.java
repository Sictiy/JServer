package com.sictiy.plugin.processor;

import java.lang.annotation.Annotation;

import org.jetbrains.annotations.NotNull;

import com.intellij.psi.PsiElement;

/**
 * @author sictiy.xu
 * @version 2019/10/15 18:28
 **/
public class AbstracProcessor implements Processor
{
    @NotNull
    @Override
    public Class<? extends Annotation>[] getSupportedAnnotations()
    {
        return new Class[0];
    }

    @NotNull
    @Override
    public Class<? extends PsiElement> getSupportedClass()
    {
        return null;
    }
}
