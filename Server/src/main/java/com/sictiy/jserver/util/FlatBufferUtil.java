package com.sictiy.jserver.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.jserver.entry.buffer.CommonMsg;

/**
 * @author 10460
 * @version 2019/10/04 20:30
 **/
public class FlatBufferUtil
{
    @SuppressWarnings("unchecked")
    public static <T> T getFlatBufferMessage(Class<T> clazz, byte[] data)
    {
        try
        {
            Method method = clazz.getMethod("getRootAs" + clazz.getSimpleName(), ByteBuffer.class);
            return (T) method.invoke(null, ByteBuffer.wrap(data));
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            LogUtil.error("", e);
        }
        return null;
    }

    public static FlatBufferBuilder newCommonMsgBuilder(String str)
    {
        return newCommonMsgBuilder(str, "", 0, 0, 0, 0);
    }

    public static FlatBufferBuilder newCommonMsgBuilder(String str1, String str2, int int1, int int2, long long1, long long2)
    {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder();
        int str1Offset = flatBufferBuilder.createString(str1);
        int str2Offset = flatBufferBuilder.createString(str2);
        CommonMsg.startCommonMsg(flatBufferBuilder);
        CommonMsg.addStr1(flatBufferBuilder, str1Offset);
        CommonMsg.addStr2(flatBufferBuilder, str2Offset);
        CommonMsg.addInt1(flatBufferBuilder, int1);
        CommonMsg.addInt2(flatBufferBuilder, int2);
        CommonMsg.addLong1(flatBufferBuilder, long1);
        CommonMsg.addLong2(flatBufferBuilder, long2);
        flatBufferBuilder.finish(CommonMsg.endCommonMsg(flatBufferBuilder));
        return flatBufferBuilder;
    }
}
