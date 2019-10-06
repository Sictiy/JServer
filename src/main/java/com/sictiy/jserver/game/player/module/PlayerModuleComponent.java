package com.sictiy.jserver.game.player.module;

import java.util.Collection;
import java.util.Map;

/**
 * @author 10460
 * @version 2019/10/05 14:28
 **/
public class PlayerModuleComponent
{
    private static Map<Class<? extends AbstractPlayerModule>, AbstractPlayerModule> allModules;

    public static boolean init()
    {
        // 加载所有玩家模块
        return true;
    }

    public static Collection<Class<? extends AbstractPlayerModule>> getAllPlayerModules()
    {
        return allModules.keySet();
    }
}
