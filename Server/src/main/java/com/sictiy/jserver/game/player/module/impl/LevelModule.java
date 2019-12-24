package com.sictiy.jserver.game.player.module.impl;

import java.util.Map;

import com.sictiy.common.db.DbComponent;
import com.sictiy.common.db.mapper.JLevelMapper;
import com.sictiy.common.db.pojo.JLevelInfo;
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
    private Map<Integer, JLevelInfo> levelInfoMap;

    @Override
    public boolean load()
    {
        var infoList = DbComponent.getInstance().getMapper(JLevelMapper.class).queryListByUserId(player.getUserId());
        infoList.forEach(info -> levelInfoMap.put(info.getType(), info));
        return true;
    }

    @Override
    public boolean save()
    {
        DbComponent.getInstance().insertOrUpdateBatch(levelInfoMap.values(), JLevelMapper.class);
        return true;
    }

    @Override
    public void sendInfo()
    {

    }
}
