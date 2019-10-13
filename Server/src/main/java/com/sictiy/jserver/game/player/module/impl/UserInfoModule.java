package com.sictiy.jserver.game.player.module.impl;

import lombok.Setter;

import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.db.mapper.JUserMapper;
import com.sictiy.jserver.db.pojo.JUserInfo;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;

/**
 * @author 10460
 * @version 2019/10/05 14:22
 **/
@Setter
public class UserInfoModule extends AbstractPlayerModule
{
    private JUserInfo userInfo;

    @Override
    public boolean load()
    {
        return true;
    }

    @Override
    public boolean save()
    {
        DbComponent.getInstance().getMapper(JUserMapper.class).updateJUser(userInfo);
        return true;
    }

    @Override
    public void sendInfo()
    {

    }

    public long getUserId()
    {
        return userInfo.getUserId();
    }
}
