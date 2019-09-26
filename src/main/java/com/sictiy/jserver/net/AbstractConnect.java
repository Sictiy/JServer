package com.sictiy.jserver.net;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import com.google.flatbuffers.FlatBufferBuilder;

/**
 * @author sictiy.xu
 * @version 2019/09/26 14:29
 **/
@Setter
@Getter
public class AbstractConnect
{
    protected Channel channel;
    protected int port;
    protected String address;

    public void send(short code, FlatBufferBuilder builder)
    {
        send(code, -1, builder);
    }

    public void send(short code, long userId, FlatBufferBuilder builder)
    {
        JMessage jMessage = new JMessage();
        jMessage.setCode(code);
        jMessage.setUserId(userId);
        jMessage.setBufferBuilder(builder);
        send(jMessage);
    }

    public void send(JMessage jMessage)
    {
        channel.writeAndFlush(jMessage);
    }
}
