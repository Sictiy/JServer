package com.sictiy.jserver.net;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.StandardCharsets;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:58
 **/
@Getter
@Setter
@ToString
public class JMessage
{
    public static short HEADER = 0xEF;
    public static short HEAD_LEN = 14;

    private short header;

    private short length;

    private short code;

    private long userId;

    private byte[] data;

    private String string;

    public byte[] readData()
    {
        if (string != null)
        {
            data = string.getBytes(StandardCharsets.ISO_8859_1);
        }
        string = null;
        return data;
    }

    public String readString()
    {
        if (data != null && data.length > 0)
        {
            string = new String(data, StandardCharsets.ISO_8859_1);
        }
        return string;
    }

    public static void encode(JMessage msg, ByteBuf byteBuf)
    {
        byteBuf.writeShort(HEADER);
        byteBuf.writeShort(msg.readData().length + HEAD_LEN);
        byteBuf.writeShort(msg.getCode());
        byteBuf.writeLong(msg.getUserId());
        byteBuf.writeBytes(msg.readData());
    }

    public static JMessage decode(ByteBuf byteBuf)
    {
        JMessage jMessage = new JMessage();
        jMessage.setHeader(byteBuf.readShort());
        jMessage.setLength(byteBuf.readShort());
        jMessage.setCode(byteBuf.readShort());
        jMessage.setUserId(byteBuf.readLong());

        int bodyLen = jMessage.getLength() - JMessage.HEAD_LEN;
        if (bodyLen > 0)
        {
            byte[] data = new byte[bodyLen];
            byteBuf.readBytes(data, 0, bodyLen);
            jMessage.setData(data);
        }
        return jMessage;
    }
}
