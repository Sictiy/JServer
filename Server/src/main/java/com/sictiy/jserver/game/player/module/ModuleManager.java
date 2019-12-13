package com.sictiy.jserver.game.player.module.impl;

import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sictiy.common.db.mapper.JModuleMapper;
import com.sictiy.common.db.pojo.JModuleInfo;
import com.sictiy.common.entry.annotation.PlayerModuleAnnotation;
import com.sictiy.common.entry.type.PlayerEventType;
import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;
import com.sictiy.jserver.game.player.module.PlayerModuleComponent;
import com.sictiy.jserver.game.player.module.pure.ModuleLogic;

/**
 * @author sictiy.xu
 * @version 2019/10/07 15:46
 **/
@Getter
public class ModuleInfoModule
{
    private JPlayer player;

    private Map<Class<? extends AbstractPlayerModule>, AbstractPlayerModule> allModules;

    private Map<Integer, JModuleInfo> moduleInfoMap;

    public ModuleInfoModule(JPlayer player)
    {
        this.player = player;
        allModules = new HashMap<>();
        moduleInfoMap = new HashMap<>();
    }

    public void checkModules()
    {
        Collection<Class<? extends AbstractPlayerModule>> allModuleClasses = PlayerModuleComponent.getInstance().getAllPlayerModules();
        allModuleClasses.forEach(aClass -> {
            PlayerModuleAnnotation annotation = aClass.getAnnotation(PlayerModuleAnnotation.class);
            if (annotation != null)
            {
                moduleInfoMap.computeIfAbsent(annotation.type(), k -> ModuleLogic.newModuleInfo(player, annotation.type()));
            }
        });
        var group = moduleInfoMap.values().stream().filter(module -> !module.isOpen()).collect(Collectors.groupingBy(info -> ModuleLogic.checkModuleOpen(player, info.getModuleType())));
        // 能开启
        group.get(true).forEach(moduleInfo -> ModuleLogic.moduleOpen(this, moduleInfo));
        // 不能开启
        group.get(false).forEach(moduleInfo -> player.subscribe(PlayerEventType.DEFAULT, objects -> ModuleLogic.checkAndOpen(player, moduleInfo)));
    }

    public void sendInfo()
    {
        allModules.values().forEach(AbstractPlayerModule::sendInfo);
    }

    private void loadModuleInfo()
    {
        List<JModuleInfo> moduleInfoList = DbComponent.getInstance().getMapper(JModuleMapper.class).queryJModuleListByUserId(player.getUserId());
        for (JModuleInfo info : moduleInfoList)
        {
            moduleInfoMap.put(info.getModuleType(), info);
        }
    }

    public void loadPlayerModules()
    {
        loadModuleInfo();
        checkModules();
        moduleInfoMap.values().stream().filter(JModuleInfo::isOpen).forEach(info ->
                allModules.computeIfAbsent(PlayerModuleComponent.getInstance().getPlayerModuleClazzByType(info.getModuleType()),
                        k -> PlayerModuleComponent.getInstance().getInstanceByModuleType(player, info.getModuleType()))
        );
        Predicate<AbstractPlayerModule> isNotNew = module -> !module.isNewOpen();
        allModules.values().stream().filter(isNotNew).forEach(AbstractPlayerModule::load);
        allModules.values().stream().filter(isNotNew).forEach(AbstractPlayerModule::check);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractPlayerModule> T getPlayerModule(Class<T> clazz)
    {
        PlayerModuleAnnotation annotation = clazz.getAnnotation(PlayerModuleAnnotation.class);
        if (annotation == null)
        {
            return null;
        }
        var moduleInfo = moduleInfoMap.get(annotation.type());
        if (moduleInfo == null || (!moduleInfo.isOpen() && ModuleLogic.checkAndOpen(player, moduleInfo)))
        {
            return null;
        }
        return (T) allModules.get(clazz);
    }

    public AbstractPlayerModule getPlayerModule(int type)
    {
        var clazz = PlayerModuleComponent.getInstance().getPlayerModuleClazzByType(type);
        return getPlayerModule(clazz);
    }

    public void addNewModule(AbstractPlayerModule module)
    {
        allModules.put(module.getClass(), module);
    }

    public boolean savePlayerModules()
    {
        allModules.values().forEach(AbstractPlayerModule::save);
        allModules.values().stream().filter(AbstractPlayerModule::isNewOpen).forEach(module -> module.setNewOpen(false));
        return true;
    }
}
