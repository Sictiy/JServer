package com.sictiy.jserver.net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:16
 **/
public class JServerInHandler extends SimpleChannelInboundHandler<JMessage>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JMessage msg)
    {
        LogUtil.info("read msg:{}", msg.readString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        LogUtil.info("active channel!");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        LogUtil.info("inactive channel!");
        super.channelInactive(ctx);
    }
}
