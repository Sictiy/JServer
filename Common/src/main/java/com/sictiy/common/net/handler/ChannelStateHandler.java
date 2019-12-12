package com.sictiy.common.net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import com.sictiy.common.net.AbstractConnect;
import com.sictiy.common.net.ICmdHandler;
import com.sictiy.common.net.JMessage;
import com.sictiy.common.net.JServerConnect;
import com.sictiy.common.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:16
 **/
public class ChannelStateHandler extends SimpleChannelInboundHandler<JMessage>
{
    private AbstractConnect abstractConnect;
    private ICmdHandler cmdHandler;

    public ChannelStateHandler(ICmdHandler cmdHandler)
    {
        abstractConnect = null;
        this.cmdHandler = cmdHandler;
    }

    public ChannelStateHandler(AbstractConnect abstractConnect)
    {
        cmdHandler = null;
        this.abstractConnect = abstractConnect;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JMessage msg)
    {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        LogUtil.info("active channel!");
        AbstractConnect connect = abstractConnect;
        if (connect == null)
        {
            connect = new JServerConnect();
            connect.setCmdHandler(cmdHandler);
        }
        connect.setChannel(ctx.channel());
        connect.setActive(true);
        ctx.channel().attr(AttributeKey.valueOf("Connect")).set(connect);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        LogUtil.info("inactive channel!");
        AbstractConnect abstractConnect = (AbstractConnect) ctx.channel().attr(AttributeKey.valueOf("Connect")).get();
        abstractConnect.onClose();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        if (ctx.channel().isActive())
        {
            ctx.close();
        }
    }
}
