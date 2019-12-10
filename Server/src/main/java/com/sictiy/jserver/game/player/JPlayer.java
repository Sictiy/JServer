package com.sictiy.jserver.game.player;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.common.entry.type.CmdType;
import com.sictiy.common.net.AbstractConnect;
import com.sictiy.common.net.IOnwer;
import com.sictiy.common.util.FlatBufferUtil;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;
import com.sictiy.jserver.game.player.module.impl.ModuleInfoModule;
import com.sictiy.jserver.game.player.module.impl.UserInfoModule;

/**
 * @author sictiy.xu
 * @version 2019/09/25 14:47
 **/
@Setter
@Getter
@ToString
public class JPlayer implements IOnwer
{
    private AbstractConnect connect;
    private ModuleInfoModule playerModuleManager;

    public JPlayer()
    {
        playerModuleManager = new ModuleInfoModule(this);
    }

    /**
     * 登录流程
     **/
    public void login()
    {
        playerModuleManager.loadPlayerModules();
        playerModuleManager.checkModules();
        playerModuleManager.sendInfo();
    }

    /**
     * 离线调用
     **/
    public void onLogout()
    {
        playerModuleManager.savePlayerModules();
    }

    public <T extends AbstractPlayerModule> T getPlayerModule(Class<T> clazz)
    {
        return playerModuleManager.getPlayerModule(clazz);
    }

    public Long getUserId()
    {
        return playerModuleManager.getPlayerModule(UserInfoModule.class).getUserId();
    }

    public void send(short code, FlatBufferBuilder flatBufferBuilder)
    {
        connect.send(code, flatBufferBuilder);
    }

    public void sendError(String string)
    {
        send(CmdType.ERROR, FlatBufferUtil.newCommonMsgBuilder(string));
    }
}
