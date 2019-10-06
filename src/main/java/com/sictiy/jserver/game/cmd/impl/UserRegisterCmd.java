package com.sictiy.jserver.game.cmd.impl;

import com.sictiy.jserver.entry.annotation.CmdAnnotation;
import com.sictiy.jserver.entry.buffer.RegisterReq;
import com.sictiy.jserver.entry.type.CmdType;
import com.sictiy.jserver.game.cmd.AbstractCmd;
import com.sictiy.jserver.game.mgr.JPlayerMgr;
import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.net.JServerConnect;
import com.sictiy.jserver.util.FlatBufferUtil;

/**
 * @author 10460
 * @version 2019/10/04 19:49
 **/
@CmdAnnotation(code = CmdType.REGISTER_REQ, desc = "注册")
public class UserRegisterCmd extends AbstractCmd
{

    @Override
    public void execute(JServerConnect connect, JMessage jMessage)
    {
        register(connect, jMessage);
    }

    private void register(JServerConnect connect, JMessage jMessage)
    {
        RegisterReq registerReq = FlatBufferUtil.getFlatBufferMessage(RegisterReq.class, jMessage.readData());
        if (registerReq != null)
        {
            JPlayer player = JPlayerMgr.register(connect, registerReq.userName(), registerReq.password());
            if (player != null)
            {
                connect.setOwner(player);
            }
        }
    }
}
