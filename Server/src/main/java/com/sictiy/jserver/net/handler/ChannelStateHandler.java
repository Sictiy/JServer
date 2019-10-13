package com.sictiy.jserver.net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import com.sictiy.jserver.net.AbstractConnect;
import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.net.JServerConnect;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:16
 **/
public class ChannelStateHandler extends SimpleChannelInboundHandler<JMessage>
{
    private AbstractConnect abstractConnect;

    public ChannelStateHandler()
    {
        abstractConnect = null;
    }

    public ChannelStateHandler(AbstractConnect abstractConnect)
    {
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
        if (abstractConnect == null)
        {
            abstractConnect = new JServerConnect();
        }
        abstractConnect.setChannel(ctx.channel());
        abstractConnect.setActive(true);
        ctx.channel().attr(AttributeKey.valueOf("Connect")).set(abstractConnect);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        LogUtil.info("inactive channel!");
        AbstractConnect abstractConnect = (AbstractConnect) ctx.channel().attr(AttributeKey.valueOf("Connect")).get();
        abstractConnect.setActive(false);
    }
}
