package com.sictiy.jserver.game.cmd.impl;

import com.sictiy.common.entry.annotation.CmdAnnotation;
import com.sictiy.common.entry.buffer.RegisterReq;
import com.sictiy.common.entry.type.CmdType;
import com.sictiy.common.net.JMessage;
import com.sictiy.common.net.JServerConnect;
import com.sictiy.common.util.FlatBufferUtil;
import com.sictiy.jserver.game.cmd.AbstractCmd;
import com.sictiy.jserver.game.mgr.JPlayerMgr;

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
        RegisterReq registerReq = FlatBufferUtil.getFlatBufferMessage(RegisterReq.class, jMessage.readData());
        if (registerReq != null)
        {
            if (JPlayerMgr.register(connect, registerReq.userName(), registerReq.password()))
            {
                connect.send(CmdType.REGISTER_RSP, FlatBufferUtil.newCommonMsgBuilder("register success"));
            }
        }
    }
}
