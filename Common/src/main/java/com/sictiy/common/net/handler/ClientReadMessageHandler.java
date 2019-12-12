package com.sictiy.common.net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.sictiy.common.entry.buffer.CommonMsg;
import com.sictiy.common.net.JMessage;
import com.sictiy.common.util.FlatBufferUtil;
import com.sictiy.common.util.LogUtil;

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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        if (ctx.channel().isActive())
        {
            ctx.close();
            LogUtil.exception(cause);
        }
    }
}
