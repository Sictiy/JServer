package com.sictiy.jserver.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:57
 **/
public class JDecoderHandler extends ByteToMessageDecoder
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
    {
        if (in.readableBytes() < JMessage.HEAD_LEN)
        {
            LogUtil.error("bytes < len! can read length:{}", in.readableBytes());
            return;
        }
        ByteBuf byteBuf = in.slice();

        short header = byteBuf.readShort();
        if (header != JMessage.HEADER)
        {
            LogUtil.error("header error! header:{}", header);
            return;
        }

        short len = byteBuf.readShort();
        if (len <= 0)
        {
            LogUtil.error("len < 0 ! len {}", len);
            return;
        }
        // 最后还是需要直接从in里面读
        out.add(JMessage.decode(in));
    }
}
