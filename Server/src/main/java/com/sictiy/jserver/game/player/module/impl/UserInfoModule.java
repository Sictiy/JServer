package com.sictiy.jserver.game.player.module.impl;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.sictiy.common.db.mapper.JUserMapper;
import com.sictiy.common.db.pojo.JUserInfo;
import com.sictiy.common.util.LogUtil;
import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;

/**
 * @author 10460
 * @version 2019/10/05 14:22
 **/
@Setter
@Getter
public class UserInfoModule extends AbstractPlayerModule
{
    private JUserInfo userInfo;
    private Date logoutTime;

    @Override
    public boolean load()
    {
        return true;
    }

    @Override
    public boolean save()
    {
        DbComponent.getInstance().getMapper(JUserMapper.class).updateJUser(userInfo);
        LogUtil.info("save info success");
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

    public String getName()
    {
        return userInfo.getUserName();
    }

}
