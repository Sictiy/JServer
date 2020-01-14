package com.sictiy.jserver.sheduler;

import com.sictiy.common.entry.annotation.JobAnnotation;
import com.sictiy.common.scheduler.AbstractSchedulerJob;
import com.sictiy.common.util.TimeUtil;
import com.sictiy.jserver.game.mgr.JPlayerMgr;
import com.sictiy.jserver.game.mgr.RunnableTaskMgr;
import com.sictiy.jserver.game.player.module.impl.UserInfoModule;

/**
 * 移除离线玩家
 *
 * @author sictiy.xu
 * @version 2019/12/12 15:56
 **/
@JobAnnotation(name = "kickOfflinePlayerJob", repeatInterval = 60)
public class KickOfflinePlayerJob extends AbstractSchedulerJob
{
    @Override
    public void execute()
    {
        // TODO change offline check time
        RunnableTaskMgr.addTask(() ->
                JPlayerMgr.getAllPlayer().stream().filter(player -> !player.isOnline())
                        .filter(player -> player.getPlayerModule(UserInfoModule.class).getLogoutTime().before(TimeUtil.getSecondsAfterDate(-2 * 60)))
                        .forEach(player ->
                        {
                            RunnableTaskMgr.addTask(() -> JPlayerMgr.kickPlayer(player));
                        })
        );
    }
}
