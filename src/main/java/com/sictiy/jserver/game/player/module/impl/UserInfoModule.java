package com.sictiy.jserver.game.player.module.impl;

import java.util.Date;

import com.sictiy.jserver.db.pojo.JUserInfo;
import com.sictiy.jserver.entry.type.UniqueType;
import com.sictiy.jserver.game.mgr.UniqueMgr;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;

/**
 * @author 10460
 * @version 2019/10/05 14:22
 **/
public class UserInfoModule extends AbstractPlayerModule
{
    private JUserInfo userInfo;

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

    public long getUserId()
    {
        return userInfo.getUserId();
    }

    public void register(String name, String password)
    {
        userInfo = new JUserInfo();
        userInfo.setUserName(name);
        userInfo.setPassword(password);
        userInfo.setCreateDate(new Date());
        userInfo.setUserId(UniqueMgr.getUnique(UniqueType.USER));
    }
}
