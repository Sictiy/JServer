package com.sictiy.jserver.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.sictiy.jserver.net.handler.JClientInHandler;
import com.sictiy.jserver.net.handler.JDecoderHandler;
import com.sictiy.jserver.net.handler.JEncoderHandler;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:10
 **/
public class JClientChannelInitializer extends ChannelInitializer<NioSocketChannel>
{
    @Override
    protected void initChannel(NioSocketChannel ch)
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new JEncoderHandler());
        pipeline.addLast(new JDecoderHandler());
        pipeline.addLast(new JClientInHandler());
    }
}
