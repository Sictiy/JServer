package com.sictiy.jserver.game.player.module;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sictiy.common.db.DbComponent;
import com.sictiy.common.db.mapper.JModuleMapper;
import com.sictiy.common.db.pojo.JModuleInfo;
import com.sictiy.common.entry.annotation.PlayerModuleAnnotation;
import com.sictiy.common.entry.type.PlayerEventType;
import com.sictiy.common.observer.Observer;
import com.sictiy.jserver.game.logic.ModuleLogic;
import com.sictiy.jserver.game.player.JPlayer;

/**
 * @author sictiy.xu
 * @version 2019/10/07 15:46
 **/
@Getter
public class ModuleManager
{
    private JPlayer player;

    private Map<Class<? extends AbstractPlayerModule>, AbstractPlayerModule> allModules;

    private Map<Integer, JModuleInfo> moduleInfoMap;

    public ModuleManager(JPlayer player)
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
        group.getOrDefault(true, Collections.emptyList()).forEach(moduleInfo -> ModuleLogic.moduleOpen(this, moduleInfo));
        // 不能开启
        group.getOrDefault(false, Collections.emptyList()).forEach(moduleInfo -> player.subscribe(PlayerEventType.DEFAULT, new Observer()
        {
            @Override
            public void update(Object... objects)
            {
                if (ModuleLogic.checkAndOpen(player, moduleInfo))
                {
                    player.unsubscribe(PlayerEventType.DEFAULT, this);
                }
            }
        }));
    }

    public void sendInfo()
    {
        allModules.values().forEach(AbstractPlayerModule::sendInfo);
    }

    private void loadModuleInfo()
    {
        List<JModuleInfo> moduleInfoList = DbComponent.getInstance().getMapper(JModuleMapper.class).queryListByUserId(player.getUserId());
        for (JModuleInfo info : moduleInfoList)
        {
            moduleInfoMap.put(info.getModuleType(), info);
        }
    }

    private void saveModuleInfo()
    {
        DbComponent.getInstance().insertOrUpdateBatch(moduleInfoMap.values(), JModuleMapper.class);
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
        if (allModules.containsKey(clazz))
        {
            return (T) allModules.get(clazz);
        }
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
        saveModuleInfo();
        allModules.values().forEach(AbstractPlayerModule::save);
        allModules.values().stream().filter(AbstractPlayerModule::isNewOpen).forEach(module -> module.setNewOpen(false));
        return true;
    }
}
