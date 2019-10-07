package com.sictiy.jserver.net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.sictiy.jserver.entry.buffer.CommonMsg;
import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.util.FlatBufferUtil;
import com.sictiy.jserver.util.LogUtil;

/**
 * 客户端接收协议handler
 * @author sictiy.xu
 * @version 2019/09/24 11:16
 **/
public class ClientReadMessageHandler extends SimpleChannelInboundHandler<JMessage>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JMessage msg)
    {
        CommonMsg commonMsg = FlatBufferUtil.getFlatBufferMessage(CommonMsg.class, msg.readData());
        if (commonMsg != null)
        {
            LogUtil.info("{}", commonMsg.str1());
        }
    }
}
