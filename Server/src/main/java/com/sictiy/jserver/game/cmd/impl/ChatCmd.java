package com.sictiy.jserver.game.cmd.impl;

import com.sictiy.common.entry.annotation.CmdAnnotation;
import com.sictiy.common.entry.buffer.ChatMsg;
import com.sictiy.common.entry.type.ChatType;
import com.sictiy.common.entry.type.CmdType;
import com.sictiy.common.net.JMessage;
import com.sictiy.common.util.FlatBufferUtil;
import com.sictiy.jserver.game.cmd.AbstractPlayerCmd;
import com.sictiy.jserver.game.mgr.JPlayerMgr;
import com.sictiy.jserver.game.player.JPlayer;

/**
 * @author sictiy.xu
 * @version 2019/10/07 17:29
 **/
@CmdAnnotation(code = CmdType.CHAT_REQ, desc = "聊天")
public class ChatCmd extends AbstractPlayerCmd
{
    @Override
    public void execute(JPlayer player, JMessage jMessage)
    {
        ChatMsg chatMsg = FlatBufferUtil.getFlatBufferMessage(ChatMsg.class, jMessage.readData());
        if (chatMsg != null)
        {
            if (chatMsg.type() == ChatType.WORLD)
            {
                JPlayerMgr.getAllPlayer().forEach(aPlayer -> aPlayer.send(CmdType.CHAT_RSP, FlatBufferUtil.newCommonMsgBuilder(chatMsg.msg())));
            }
            else
            {
                player.send(CmdType.CHAT_RSP, FlatBufferUtil.newCommonMsgBuilder(chatMsg.msg()));
            }
        }
    }
}
