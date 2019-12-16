package com.sictiy.jserver.game.player.module.impl;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.sictiy.common.db.mapper.JUserMapper;
import com.sictiy.common.db.pojo.JUserInfo;
import com.sictiy.common.entry.annotation.PlayerModuleAnnotation;
import com.sictiy.common.entry.type.PlayerModuleType;
import com.sictiy.common.util.LogUtil;
import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;

/**
 * @author 10460
 * @version 2019/10/05 14:22
 **/
@Setter
@Getter
@PlayerModuleAnnotation(type = PlayerModuleType.USER_INFO)
public class UserInfoModule extends AbstractPlayerModule
{
    // 玩家身上的引用
    private JUserInfo userInfo;
    private Date logoutTime;

    @Override
    public boolean load()
    {
        userInfo = player.getUserInfo();
        return true;
    }

    @Override
    public boolean save()
    {
        DbComponent.getInstance().insertOrUpdate(userInfo, JUserMapper.class);
        LogUtil.info("save info success");
        return true;
    }

    @Override
    public void sendInfo()
    {

    }
}
