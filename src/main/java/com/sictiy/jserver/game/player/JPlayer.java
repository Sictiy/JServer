package com.sictiy.jserver.game.player;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sictiy.jserver.game.player.module.AbstractPlayerModule;
import com.sictiy.jserver.game.player.module.PlayerModuleComponent;
import com.sictiy.jserver.game.player.module.impl.UserInfoModule;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/25 14:47
 **/
@Setter
@Getter
@ToString
public class JPlayer
{
    private Map<Class<? extends AbstractPlayerModule>, AbstractPlayerModule> allModules;

    public JPlayer()
    {
        allModules = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public boolean loadPlayerModules()
    {
        Collection<Class<? extends AbstractPlayerModule>> allModuleClasses = PlayerModuleComponent.getAllPlayerModules();
        allModuleClasses.forEach(aClass -> {
            try
            {
                AbstractPlayerModule moduleImpl = aClass.getDeclaredConstructor().newInstance();
                moduleImpl.setPlayer(this);
                allModules.put(aClass, moduleImpl);
            }
            catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
            {
                LogUtil.error("", e);
            }
        });
        allModules.values().forEach(AbstractPlayerModule::load);
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractPlayerModule> T getPlayerModule(Class<T> clazz)
    {
        if (!allModules.containsKey(clazz))
        {
            try
            {
                AbstractPlayerModule playerModule = clazz.getDeclaredConstructor().newInstance();
                playerModule.setPlayer(this);
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

    public Long getUserId()
    {
        return getPlayerModule(UserInfoModule.class).getUserId();
    }
}
