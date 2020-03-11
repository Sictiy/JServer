package com.sictiy.plugin.processor;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * 处理接口
 *
 * @author sictiy.xu
 * @version 2019/10/15 16:43
 **/
public interface Processor
{
    @NotNull
    Class<? extends Annotation>[] getSupportedAnnotations();

    @NotNull
    Class<? extends PsiElement> getSupportedClass();

    /**
     * 对psi类进行处理
     *
     * @param psiClass psiClass
     * @return java.util.List<? super com.intellij.psi.PsiElement>
     **/
    default List<? super PsiElement> process(@NotNull PsiClass psiClass)
    {
        return Collections.emptyList();
    }

    /**
     * 当前配置是否开放当前处理
     *
     * @param component component
     * @return boolean
     **/
    default boolean isEnable(@NotNull PropertiesComponent component)
    {
        return true;
    }

}
