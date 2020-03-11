package com.sictiy.plugin.psi;

import org.jetbrains.annotations.NotNull;

import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.light.LightFieldBuilder;

/**
 * @author sictiy.xu
 * @version 2020/03/10 15:22
 **/
public class MyLightFieldBuilder extends LightFieldBuilder
{
    public MyLightFieldBuilder(PsiManager manager, @NotNull String name, @NotNull PsiType type)
    {
        super(manager, name, type);
    }
}
