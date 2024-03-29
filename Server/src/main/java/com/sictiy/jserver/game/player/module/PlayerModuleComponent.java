package com.sictiy.jserver.game.player.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sictiy.common.entry.annotation.PlayerModuleAnnotation;
import com.sictiy.common.util.ClassUtil;
import com.sictiy.common.util.LogUtil;
import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.processor.single.SingleInstance;

/**
 * @author 10460
 * @version 2019/10/05 14:28
 **/
@SingleInstance
public class PlayerModuleComponent
{
    private Map<Integer, Class<? extends AbstractPlayerModule>> allModules;

    public boolean init()
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

    public Collection<Class<? extends AbstractPlayerModule>> getAllPlayerModules()
    {
        return allModules.values();
    }

    public Class<? extends AbstractPlayerModule> getPlayerModuleClazzByType(int type)
    {
        return allModules.get(type);
    }

    public AbstractPlayerModule getInstanceByModuleType(JPlayer player, int type)
    {
        var clazz = allModules.get(type);
        try
        {
            var module = clazz.getDeclaredConstructor().newInstance();
            module.setPlayer(player);
            return module;
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
        }
        return null;
    }
}
