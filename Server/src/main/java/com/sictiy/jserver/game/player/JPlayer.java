package com.sictiy.jserver.game.player;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.common.db.DataOwner;
import com.sictiy.common.db.pojo.JUserInfo;
import com.sictiy.common.entry.type.CmdType;
import com.sictiy.common.executor.TaskQueue;
import com.sictiy.common.net.AbstractConnect;
import com.sictiy.common.net.IOwner;
import com.sictiy.common.observer.AbstractSubject;
import com.sictiy.common.util.FlatBufferUtil;
import com.sictiy.jserver.game.mgr.ExecutorMgr;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;
import com.sictiy.jserver.game.player.module.ModuleManager;
import com.sictiy.jserver.game.player.module.impl.UserInfoModule;

/**
 * @author sictiy.xu
 * @version 2019/09/25 14:47
 **/
@Setter
@Getter
@ToString
public class JPlayer extends AbstractSubject implements IOwner, DataOwner
{
    private AbstractConnect connect;
    private ModuleManager playerModuleManager;
    private TaskQueue<Runnable> taskQueue;
    private JUserInfo userInfo;

    public JPlayer(JUserInfo userInfo)
    {
        this.userInfo = userInfo;
        taskQueue = new TaskQueue<>(ExecutorMgr.getJExecutor(ExecutorMgr.PLAYER_EXECUTOR));
        playerModuleManager = new ModuleManager(this);
    }

    /**
     * 登录流程
     **/
    public void login()
    {
        playerModuleManager.loadPlayerModules();
        playerModuleManager.sendInfo();
    }

    /**
     * 玩家断线
     **/
    @Override
    public void onDropLine()
    {
        addTask(this::onLogout);
    }

    /**
     * 离线调用
     **/
    public void onLogout()
    {
        connect = null;
        playerModuleManager.getPlayerModule(UserInfoModule.class).setLogoutTime(new Date());
        playerModuleManager.savePlayerModules();
    }

    /**
     * 外部get
     *****************************************************************************/
    public <T extends AbstractPlayerModule> T getPlayerModule(Class<T> clazz)
    {
        return playerModuleManager.getPlayerModule(clazz);
    }

    @Override
    public long getUserId()
    {
        return userInfo.getUserId();
    }

    public String getName()
    {
        return userInfo.getUserName();
    }

    public boolean isOnline()
    {
        return connect != null;
    }

    /**
     * 协议
     ********************************************/
    public void send(short code, FlatBufferBuilder flatBufferBuilder)
    {
        connect.send(code, flatBufferBuilder);
    }

    public void sendError(String string)
    {
        send(CmdType.ERROR, FlatBufferUtil.newCommonMsgBuilder(string));
    }

    /**
     * 队列
     ***************************************************************/
    public void addTask(Runnable runnable)
    {
        taskQueue.submit(runnable);
    }
}
