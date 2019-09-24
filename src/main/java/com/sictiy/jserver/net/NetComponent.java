package com.sictiy.jserver.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

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

    public static void start(int port, JServerChannelInitializer initializer)
    {
        try
        {
            boss = new NioEventLoopGroup();
            work = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            bootstrap.localAddress(new InetSocketAddress(port));
            bootstrap.childHandler(initializer);
            channelFuture = bootstrap.bind().sync();
        }
        catch (InterruptedException e)
        {
            LogUtil.error("", e);
        }
    }

    public static void stop()
    {
        channelFuture.channel().closeFuture().syncUninterruptibly();
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
    }
}
