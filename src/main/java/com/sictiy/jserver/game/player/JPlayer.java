package com.sictiy.jserver.game.player;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.jserver.entry.type.CmdType;
import com.sictiy.jserver.game.player.module.AbstractPlayerModule;
import com.sictiy.jserver.game.player.module.impl.ModuleInfoModule;
import com.sictiy.jserver.game.player.module.impl.UserInfoModule;
import com.sictiy.jserver.net.AbstractConnect;
import com.sictiy.jserver.util.FlatBufferUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/25 14:47
 **/
@Setter
@Getter
@ToString
public class JPlayer
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
