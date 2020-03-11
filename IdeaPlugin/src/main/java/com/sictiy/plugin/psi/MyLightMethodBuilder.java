package com.sictiy.plugin.psi;

import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.light.LightMethodBuilder;

/**
 * @author sictiy.xu
 * @version 2020/03/10 15:26
 **/
public class MyLightMethodBuilder extends LightMethodBuilder
{
    public MyLightMethodBuilder(PsiManager manager, String name)
    {
        super(manager, name);
    }
}
