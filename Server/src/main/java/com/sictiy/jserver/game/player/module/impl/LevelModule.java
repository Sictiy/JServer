package com.sictiy.jserver.game.player.module.impl;

import com.sictiy.common.entry.annotation.PlayerModuleAnnotation;
import com.sictiy.common.entry.type.PlayerModuleType;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;

/**
 * 等级
 *
 * @author sictiy.xu
 * @version 2019/12/13 14:47
 **/
@PlayerModuleAnnotation(type = PlayerModuleType.LEVEL)
public class LevelModule extends AbstractPlayerModule
{

    @Override
    public boolean load()
    {
        return false;
    }

    @Override
    public boolean save()
    {
        return false;
    }

    @Override
    public void sendInfo()
    {

    }
}
