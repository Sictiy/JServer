package com.sictiy.jserver.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import com.sictiy.jserver.net.handler.JServerInHandler;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:10
 **/
public class JServerChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new JServerInHandler());
    }
}
