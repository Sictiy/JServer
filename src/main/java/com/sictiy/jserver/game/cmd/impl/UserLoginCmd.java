package com.sictiy.jserver.game.cmd.impl;

import com.sictiy.jserver.entry.annotation.CmdAnnotation;
import com.sictiy.jserver.entry.buffer.LoginReq;
import com.sictiy.jserver.entry.type.CmdType;
import com.sictiy.jserver.game.cmd.AbstractCmd;
import com.sictiy.jserver.game.mgr.JPlayerMgr;
import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.net.JServerConnect;
import com.sictiy.jserver.util.FlatBufferUtil;

/**
 * @author sictiy.xu
 * @version 2019/10/07 17:14
 **/
@CmdAnnotation(code = CmdType.LOGIN_REQ, desc = "登录")
public class UserLoginCmd extends AbstractCmd
{
    @Override
    public void execute(JServerConnect connect, JMessage jMessage)
    {
        LoginReq loginReq = FlatBufferUtil.getFlatBufferMessage(LoginReq.class, jMessage.readData());
        if (loginReq != null)
        {
            JPlayer player = JPlayerMgr.login(connect, loginReq.userName(), loginReq.password());
            if (player != null)
            {
                connect.setOwner(player);
                player.send(CmdType.LOGIN_RSP, FlatBufferUtil.newCommonMsgBuilder("login success"));
            }
        }
    }
}
