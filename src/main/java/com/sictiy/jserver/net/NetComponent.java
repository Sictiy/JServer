package com.sictiy.jserver.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/24 12:22
 **/
public class NetComponent
{
    private static EventLoopGroup boss;
    private static EventLoopGroup work;
    private static ChannelFuture channelFuture;

    private static Map<Integer, JConnect> connectMap = new HashMap<>();

    public static void start(int port, ChannelInitializer initializer)
    {
        try
        {
            boss = new NioEventLoopGroup();
            work = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.localAddress(new InetSocketAddress(port));
            bootstrap.childHandler(initializer);
            channelFuture = bootstrap.bind().sync();
        }
        catch (InterruptedException e)
        {
            LogUtil.error("", e);
        }
    }

    public static JConnect getConnection(JServerConfig config)
    {
        return connectMap.computeIfAbsent(config.getId(), k -> JConnect.newConnect(config.getPort(), config.getAddress()));
    }

    public static void stop()
    {
        channelFuture.channel().closeFuture().syncUninterruptibly();
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
    }
}
