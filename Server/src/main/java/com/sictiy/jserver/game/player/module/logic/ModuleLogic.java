package com.sictiy.jserver.game.player.module.logic;

import com.sictiy.common.db.DataObject;
import com.sictiy.common.db.pojo.JModuleInfo;
import com.sictiy.common.entry.type.UniqueType;
import com.sictiy.jserver.game.mgr.UniqueMgr;
import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.game.player.module.ModuleManager;
import com.sictiy.jserver.game.player.module.PlayerModuleComponent;

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
        var moduleInfo = DataObject.newDataObject(JModuleInfo.class);
        moduleInfo.setId(UniqueMgr.getUnique(UniqueType.MODULE));
        moduleInfo.setModuleType(type);
        moduleInfo.setUserId(player.getUserId());
        moduleInfo.setOpen(false);
        return moduleInfo;
    }

    public static void moduleOpen(ModuleManager moduleManager, JModuleInfo moduleInfo)
    {
        moduleInfo.setOpen(true);
        var module = PlayerModuleComponent.getInstance().getInstanceByModuleType(moduleManager.getPlayer(), moduleInfo.getModuleType());
        module.open();
        module.setNewOpen(true);
        moduleManager.addNewModule(module);
    }
}
