package com.sictiy.plugin.psi;

import org.jetbrains.annotations.NotNull;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.light.LightPsiClassBuilder;

/**
 * @author sictiy.xu
 * @version 2020/03/11 17:05
 **/
public class MyLightClassBuilder extends LightPsiClassBuilder
{
    public MyLightClassBuilder(@NotNull PsiElement context, @NotNull String name)
    {
        super(context, name);
    }
}
