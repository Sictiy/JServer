package com.sictiy.plugin.action;


import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sictiy.common.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/10/15 16:04
 **/
public class SingleAction extends AnAction
{
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent)
    {
        LogUtil.info("click single action");
    }
}
