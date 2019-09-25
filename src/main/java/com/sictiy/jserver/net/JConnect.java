package com.sictiy.jserver.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Getter;

/**
 * @author sictiy.xu
 * @version 2019/09/25 11:23
 **/
@Getter
public class JConnect
{
    private Channel channel;
    private EventLoopGroup group;

    public static JConnect newConnect(int port, String address)
    {
        JConnect jConnect = new JConnect();
        jConnect.connect(port, address);
        return jConnect;
    }

    public void connect(int port, String address)
    {
        Bootstrap bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        bootstrap.handler(new JServerChannelInitializer());
        channel = bootstrap.connect(address, port).channel();
    }

    public void send(JMessage jMessage)
    {
        channel.writeAndFlush(jMessage);

    }

    public void close()
    {
        channel.close().syncUninterruptibly();
        channel.closeFuture().syncUninterruptibly();
        group.shutdownGracefully().syncUninterruptibly();
    }
}
