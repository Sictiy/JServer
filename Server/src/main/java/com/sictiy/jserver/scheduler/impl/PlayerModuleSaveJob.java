package com.sictiy.jserver.scheduler.impl;

import com.sictiy.common.entry.annotation.JobAnnotation;
import com.sictiy.jserver.game.mgr.JPlayerMgr;
import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.scheduler.AbstractSchedulerJob;

/**
 * 模块数据保存
 *
 * @author sictiy.xu
 * @version 2019/12/12 11:19
 **/
@JobAnnotation(name = "playerModuleSaveJob", repeatInterval = 60)
public class PlayerModuleSaveJob extends AbstractSchedulerJob
{
    @Override
    public void execute()
    {
        JPlayerMgr.getAllConditionPlayer(JPlayer::isOnline).forEach(jPlayer -> jPlayer.addTask(() -> jPlayer.getPlayerModuleManager().savePlayerModules()));
    }
}
