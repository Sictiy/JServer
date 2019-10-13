package com.sictiy.jserver.game.player.module.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;
import com.sictiy.jserver.game.player.module.PlayerModuleComponent;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/10/07 15:46
 **/
public class ModuleInfoModule
{
    private JPlayer player;

    private Map<Class<? extends AbstractPlayerModule>, AbstractPlayerModule> allModules;

    public ModuleInfoModule(JPlayer player)
    {
        this.player = player;
        allModules = new HashMap<>();
    }

    public void checkModules()
    {

    }

    public void sendInfo()
    {

    }

    public void loadPlayerModules()
    {
        Collection<Class<? extends AbstractPlayerModule>> allModuleClasses = PlayerModuleComponent.getInstance().getAllPlayerModules();
        allModuleClasses.forEach(aClass -> allModules.computeIfAbsent(aClass, k -> {
            AbstractPlayerModule moduleImpl = null;
            try
            {
                moduleImpl = aClass.getDeclaredConstructor().newInstance();
            }
            catch (Exception e)
            {
                LogUtil.exception(e);
            }
            return moduleImpl;
        }));
        allModules.values().forEach(AbstractPlayerModule::load);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractPlayerModule> T getPlayerModule(Class<T> clazz)
    {
        if (!allModules.containsKey(clazz))
        {
            try
            {
                AbstractPlayerModule playerModule = clazz.getDeclaredConstructor().newInstance();
                playerModule.setPlayer(player);
                allModules.put(clazz, playerModule);
            }
            catch (InstantiationException e)
            {
                LogUtil.error("", e);
            }
            catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return (T) allModules.get(clazz);
    }

    public AbstractPlayerModule getPlayerModule(short type)
    {
        var clazz = PlayerModuleComponent.getInstance().getPlayerModuleClazzByType(type);
        return getPlayerModule(clazz);
    }
}
