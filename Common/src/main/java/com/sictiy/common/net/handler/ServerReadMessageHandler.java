package com.sictiy.common.net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import com.sictiy.common.net.AbstractConnect;
import com.sictiy.common.net.CmdHandler;
import com.sictiy.common.net.JMessage;

/**
 * 服务器读取协议handler
 *
 * @author sictiy.xu
 * @version 2019/09/24 11:16
 **/
public class ServerReadMessageHandler extends SimpleChannelInboundHandler<JMessage>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JMessage msg)
    {
        AbstractConnect abstractConnect = (AbstractConnect) ctx.channel().attr(AttributeKey.valueOf("Connect")).get();
        CmdHandler.handlerCmdMessage(abstractConnect, msg);
    }
}
