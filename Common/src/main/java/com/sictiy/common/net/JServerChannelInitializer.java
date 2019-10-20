package com.sictiy.common.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.sictiy.common.net.handler.ChannelStateHandler;
import com.sictiy.common.net.handler.JDecoderHandler;
import com.sictiy.common.net.handler.JEncoderHandler;
import com.sictiy.common.net.handler.ServerReadMessageHandler;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:10
 **/
public class JServerChannelInitializer extends ChannelInitializer<NioSocketChannel>
{
    private ICmdHandler cmdHandler;

    public JServerChannelInitializer(ICmdHandler cmdHandler)
    {
        this.cmdHandler = cmdHandler;
    }

    @Override
    protected void initChannel(NioSocketChannel ch)
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ChannelStateHandler(cmdHandler));
        pipeline.addLast(new JEncoderHandler());
        pipeline.addLast(new JDecoderHandler());
        pipeline.addLast(new ServerReadMessageHandler());
    }
}
