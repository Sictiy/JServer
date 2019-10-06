package com.sictiy.jserver.net;

import io.netty.bootstrap.Bootstrap;
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
public class JClientConnect extends AbstractConnect
{
    private EventLoopGroup group;

    public static JClientConnect newConnect(int port, String address)
    {
        JClientConnect jConnect = new JClientConnect();
        jConnect.connect(port, address);
        return jConnect;
    }

    public void connect(int port, String address)
    {
        this.address = address;
        this.port = port;

        Bootstrap bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        bootstrap.handler(new JClientChannelInitializer(this));
        channel = bootstrap.connect(address, port).channel();
    }

    public void close()
    {
        channel.close().syncUninterruptibly();
        channel.closeFuture().syncUninterruptibly();
        group.shutdownGracefully().syncUninterruptibly();
    }
}
