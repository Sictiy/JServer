package com.sictiy.plugin.processor;

import java.lang.annotation.Annotation;

import org.jetbrains.annotations.NotNull;

import com.intellij.psi.PsiElement;

/**
 * 处理接口
 *
 * @author sictiy.xu
 * @version 2019/10/15 16:43
 **/
public interface Processor
{
    // 获取支持的注解
    @NotNull
    Class<? extends Annotation>[] getSupportedAnnotations();

    // 获取支持的类
    @NotNull
    Class<? extends PsiElement> getSupportedClass();
}
