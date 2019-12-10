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
import com.sictiy.processor.single.SingleInstance;

/**
 * @author sictiy.xu
 * @version 2019/09/24 12:22
 **/
@SingleInstance
public class NetComponent
{
    private EventLoopGroup boss;
    private EventLoopGroup work;
    private ChannelFuture channelFuture;
    private int port;
    private ChannelInitializer initializer;

    private Map<Integer, JClientConnect> connectMap = new HashMap<>();

    public void set(int port, ChannelInitializer initializer)
    {
        this.port = port;
        this.initializer = initializer;
    }

    public boolean init()
    {
        try
        {
            boss = new NioEventLoopGroup();
            work = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(new InetSocketAddress(port));
            bootstrap.childHandler(initializer);
            channelFuture = bootstrap.bind().sync();
        }
        catch (InterruptedException e)
        {
            LogUtil.error("", e);
            return false;
        }
        return true;
    }

    public JClientConnect getConnection(JServerConfig config)
    {
        return connectMap.computeIfAbsent(config.getId(), k -> JClientConnect.newConnect(config.getPort(), config.getAddress()));
    }

    public void stop()
    {
        channelFuture.channel().closeFuture().syncUninterruptibly();
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
    }
}
