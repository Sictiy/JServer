package com.sictiy.jserver.game.player;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.common.db.pojo.JUserInfo;
import com.sictiy.common.entry.executor.TaskQueue;
import com.sictiy.common.entry.type.CmdType;
import com.sictiy.common.net.AbstractConnect;
import com.sictiy.common.net.IOnwer;
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
public class JPlayer extends AbstractSubject implements IOnwer
{
    private AbstractConnect connect;
    private ModuleManager playerModuleManager;
    private TaskQueue<Runnable> taskQueue;
    private JUserInfo userInfo;
    boolean online;

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
        online = true;
        playerModuleManager.loadPlayerModules();
        playerModuleManager.sendInfo();
    }

    /**
     * 离线调用
     **/
    public void onLogout()
    {
        online = false;
        playerModuleManager.getPlayerModule(UserInfoModule.class).setLogoutTime(new Date());
        playerModuleManager.savePlayerModules();
    }

    public <T extends AbstractPlayerModule> T getPlayerModule(Class<T> clazz)
    {
        return playerModuleManager.getPlayerModule(clazz);
    }

    public Long getUserId()
    {
        return userInfo.getUserId();
    }

    public String getName()
    {
        return userInfo.getUserName();
    }

    public void send(short code, FlatBufferBuilder flatBufferBuilder)
    {
        connect.send(code, flatBufferBuilder);
    }

    public void sendError(String string)
    {
        send(CmdType.ERROR, FlatBufferUtil.newCommonMsgBuilder(string));
    }

    public void addTask(Runnable runnable)
    {
        taskQueue.submit(runnable);
    }

    @Override
    public void onDropLine()
    {
        addTask(this::onLogout);
    }
}
