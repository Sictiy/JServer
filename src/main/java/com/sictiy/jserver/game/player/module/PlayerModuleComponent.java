package com.sictiy.jserver.game.player.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sictiy.jserver.entry.annotation.PlayerModuleAnnotation;
import com.sictiy.jserver.util.ClassUtil;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author 10460
 * @version 2019/10/05 14:28
 **/
public class PlayerModuleComponent
{
    private static Map<Short, Class<? extends AbstractPlayerModule>> allModules;

    public static boolean init()
    {
        try
        {
            allModules = new HashMap<>();
            var classes = ClassUtil.getImplClassByAbstractClass(AbstractPlayerModule.class);
            for (var clazz : classes)
            {
                var annotation = clazz.getAnnotation(PlayerModuleAnnotation.class);
                if (annotation == null)
                {
                    continue;
                }
                allModules.put(annotation.type(), clazz);
            }
        }
        catch (Exception e)
        {
            LogUtil.error("", e);
        }
        return true;
    }

    public static Collection<Class<? extends AbstractPlayerModule>> getAllPlayerModules()
    {
        return allModules.values();
    }

    public static Class<? extends AbstractPlayerModule> getPlayerModuleClazzByType(short type)
    {
        return allModules.get(type);
    }

    public static AbstractPlayerModule getInstanceByModuleType(short type)
    {
        var clazz = allModules.get(type);
        try
        {
            return clazz.getDeclaredConstructor().newInstance();
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
        }
        return null;
    }
}
