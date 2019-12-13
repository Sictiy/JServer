package com.sictiy.jserver.game.player.module.pure;

import com.sictiy.common.db.pojo.JModuleInfo;
import com.sictiy.common.entry.type.UniqueType;
import com.sictiy.jserver.game.mgr.UniqueMgr;
import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.game.player.module.PlayerModuleComponent;
import com.sictiy.jserver.game.player.module.impl.ModuleInfoModule;

/**
 * 模块逻辑
 *
 * @author sictiy.xu
 * @version 2019/12/13 17:16
 **/
public class ModuleLogic
{
    public static boolean checkAndOpen(JPlayer player, JModuleInfo moduleInfo)
    {
        if (checkModuleOpen(player, moduleInfo.getModuleType()))
        {
            moduleOpen(player.getPlayerModuleManager(), moduleInfo);
            return true;
        }
        return false;
    }

    public static boolean checkModuleOpen(JPlayer player, int type)
    {
        return true;
    }

    public static JModuleInfo newModuleInfo(JPlayer player, int type)
    {
        var moduleInfo = new JModuleInfo();
        moduleInfo.setId(UniqueMgr.getUnique(UniqueType.MODULE));
        moduleInfo.setModuleType(type);
        moduleInfo.setUserId(player.getUserId());
        moduleInfo.setOpen(false);
        return moduleInfo;
    }

    public static void moduleOpen(ModuleInfoModule moduleInfoModule, JModuleInfo moduleInfo)
    {
        moduleInfo.setOpen(true);
        var module = PlayerModuleComponent.getInstance().getInstanceByModuleType(moduleInfoModule.getPlayer(), moduleInfo.getModuleType());
        module.open();
        module.setNewOpen(true);
        moduleInfoModule.addNewModule(module);
    }
}
