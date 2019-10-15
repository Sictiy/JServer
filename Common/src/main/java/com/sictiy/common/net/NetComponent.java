package com.sictiy.common.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sictiy.common.config.xml.JServerConfig;
import com.sictiy.common.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/24 12:22
 **/
public class NetComponent
{
    private static EventLoopGroup boss;
    private static EventLoopGroup work;
    private static ChannelFuture channelFuture;

    private static Map<Integer, JClientConnect> connectMap = new HashMap<>();

    public static void start(int port, ChannelInitializer initializer)
    {
        try
        {
            boss = new NioEventLoopGroup();
            work = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            bootstrap.channel(NioServerSocketChannel.class);
            //            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.localAddress(new InetSocketAddress(port));
            bootstrap.childHandler(initializer);
            channelFuture = bootstrap.bind().sync();
        }
        catch (InterruptedException e)
        {
            LogUtil.error("", e);
        }
    }

    public static JClientConnect getConnection(JServerConfig config)
    {
        return connectMap.computeIfAbsent(config.getId(), k -> JClientConnect.newConnect(config.getPort(), config.getAddress()));
    }

    public static void stop()
    {
        channelFuture.channel().closeFuture().syncUninterruptibly();
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
    }
}
