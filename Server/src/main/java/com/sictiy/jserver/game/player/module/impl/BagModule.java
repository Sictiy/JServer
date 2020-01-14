package com.sictiy.jserver.game.player.module.impl;

import java.util.Map;

import com.sictiy.common.bag.CommonBag;
import com.sictiy.common.entry.annotation.PlayerModuleAnnotation;
import com.sictiy.common.entry.type.PlayerModuleType;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;

/**
 * 背包
 *
 * @author sictiy.xu
 * @version 2019/12/23 17:00
 **/
@PlayerModuleAnnotation(type = PlayerModuleType.BAG)
public class BagModule extends AbstractPlayerModule
{
    private Map<Integer, CommonBag> allBags;

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

    /**
     * 外部方法
     *******************************************************************/
    public boolean addItem(int itemId, int count, int source)
    {
        return true;
    }

    public boolean useItem(int itemId, int count, int source)
    {
        return true;
    }

    public boolean payItem(int itemId, int count, int source)
    {
        return true;
    }
}
