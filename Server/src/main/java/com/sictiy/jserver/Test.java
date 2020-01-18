package com.sictiy.jserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.common.config.ConfigComponent;
import com.sictiy.common.config.xml.JServerConfig;
import com.sictiy.common.db.DataObject;
import com.sictiy.common.db.DbComponent;
import com.sictiy.common.db.mapper.JUserMapper;
import com.sictiy.common.db.pojo.JUserInfo;
import com.sictiy.common.entry.buffer.RegisterReq;
import com.sictiy.common.rpc.RpcComponent;
import com.sictiy.common.util.ClassUtil;
import com.sictiy.common.util.LogUtil;
import com.sictiy.common.util.TimeUtil;
import com.sictiy.jserver.game.cmd.AbstractPlayerCmd;

public class Test
{
    public static void main(String[] args) throws IOException
    {
        //        testResource();
        //        testDb();
        testStream();
        //        testStreamAndFor();
    }

    private static void testStream()
    {
        String[] words = {"hello", "my", "world"};
        var strStream = Arrays.stream(words);
        var tempStream = strStream.filter(word -> word.length() > 3)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .map(String::toUpperCase)
                .sorted();
        var result = tempStream.reduce((a, b) -> a + "," + b);
        RpcComponent.getInstance().init("consumer.xml");
        LogUtil.info(result.orElse("null"));
    }

    private static void testStreamAndFor()
    {
        Collection<Integer> ints = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < 100; i++)
        {
            ints.add(i);
        }
        long time = TimeUtil.getCurrentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            var tempList = new ArrayList<Integer>();
            for (int j : ints)
            {
                if ((j >= 50) && (j & 1) == 1)
                {
                    tempList.add(j);
                }
            }
            for (int j = 0; j < tempList.size(); j++)
            {
                tempList.set(j, tempList.get(j) + 10);
            }
            sum = 0;
            for (int j : tempList)
            {
                sum += j;
            }
        }
        LogUtil.info("{}", sum);
        LogUtil.info("time:{}", TimeUtil.getCurrentTimeMillis() - time);
        time = TimeUtil.getCurrentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            sum = 0;
            for (int j : ints)
            {
                if ((j >= 50) && (j & 1) == 1)
                {
                    sum += j + 10;
                }
            }
        }
        LogUtil.info("{}", sum);
        LogUtil.info("time:{}", TimeUtil.getCurrentTimeMillis() - time);
        time = TimeUtil.getCurrentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            sum = ints.stream().filter(j -> j >= 50 && (j & 1) == 1).map(j -> j + 10).reduce(Integer::sum).orElse(0);
        }
        LogUtil.info("{}", sum);
        LogUtil.info("time:{}", TimeUtil.getCurrentTimeMillis() - time);
    }

    private static void testResource()
    {
        String a = ConfigComponent.getInstance().getClass().getResource("/").getPath();
        LogUtil.info("{}", a);
    }

    private static void testClassUtil()
    {
        Set<Class> classes = ClassUtil.getClassByPackage("com.sictiy.jserver.game.cmd.impl");
        classes.forEach(aClass -> LogUtil.info("{}", aClass));
        var implClass = ClassUtil.getImplClassByAbstractClass(AbstractPlayerCmd.class);
        implClass.forEach(aClass -> LogUtil.info("{}", aClass));
    }

    private static void testFlat()
    {
        FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();
        int userName = bufferBuilder.createString("test");
        int password = bufferBuilder.createString("password");
        //        RegisterReq.startRegisterReq(bufferBuilder);
        //        RegisterReq.addUserName(bufferBuilder, userName);
        //        RegisterReq.addPassword(bufferBuilder, password);
        //        bufferBuilder.finish(RegisterReq.endRegisterReq(bufferBuilder));
        bufferBuilder.finish(RegisterReq.createRegisterReq(bufferBuilder, userName, password));
        byte[] data = bufferBuilder.sizedByteArray();
        RegisterReq registerReq = RegisterReq.getRootAsRegisterReq(ByteBuffer.wrap(data));
        LogUtil.info("{}", registerReq.password());
        LogUtil.info("{}", registerReq.userName());
    }

    private static void testDb()
    {
        DbComponent.getInstance().init();
        JUserMapper userMapper = DbComponent.getInstance().getMapper(JUserMapper.class);

        List<JUserInfo> allUserInfo = userMapper.queryAll();
        allUserInfo.forEach(info -> LogUtil.info("{}", info));
        var jUserInfo = DataObject.newDataObject(JUserInfo.class);
        jUserInfo.setCreateDate(new Date());
        jUserInfo.setPassword("123");
        jUserInfo.setUserId(allUserInfo.size() + 1);
        jUserInfo.setUserName("test");
        DbComponent.getInstance().insertOrUpdate(jUserInfo, JUserMapper.class);

        JUserInfo selectUserInfo = userMapper.queryByUserId((long) allUserInfo.size() + 1);
        LogUtil.info("{}", selectUserInfo);
    }

    private static void testConfig()
    {
        JServerConfig jServerConfig = ConfigComponent.getInstance().getConfig(JServerConfig.class);
        LogUtil.info("{}", jServerConfig);
    }
}
