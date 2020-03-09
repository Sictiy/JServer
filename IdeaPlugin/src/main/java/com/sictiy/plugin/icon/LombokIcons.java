package com.sictiy.plugin.icon;

import javax.swing.*;

import com.intellij.openapi.util.IconLoader;

public class LombokIcons
{
    private static Icon load(String path)
    {
        return IconLoader.getIcon(path, LombokIcons.class);
    }

    public static final Icon CLASS_ICON = load("/icons/nodes/lombokClass.png");
    public static final Icon FIELD_ICON = load("/icons/nodes/lombokField.png");
    public static final Icon METHOD_ICON = load("/icons/nodes/lombokMethod.png");

    public static final Icon CONFIG_FILE_ICON = load("/icons/config.png");
}
