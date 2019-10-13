package com.sictiy.jserver.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.sictiy.jserver.net.JMessage;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:57
 **/
public class JEncoderHandler extends MessageToMessageEncoder<JMessage>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, JMessage msg, List<Object> out)
    {
        int totalLen = JMessage.HEAD_LEN + msg.readData().length;
        ByteBuf byteBuf = ctx.alloc().buffer(totalLen);
        JMessage.encode(msg, byteBuf);
        out.add(byteBuf);
    }
}
