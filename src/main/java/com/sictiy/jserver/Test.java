package com.sictiy.jserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.db.mapper.JUserMapper;
import com.sictiy.jserver.db.pojo.JUserInfo;
import com.sictiy.jserver.entry.buffer.RegisterReq;
import com.sictiy.jserver.game.cmd.AbstractPlayerCmd;
import com.sictiy.jserver.util.ClassUtil;
import com.sictiy.jserver.util.LogUtil;

public class Test
{
    public static void main(String[] args) throws IOException
    {
        testClassUtil();
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
        DbComponent.init();
        JUserMapper userMapper = DbComponent.getMapper(JUserMapper.class);

        List<JUserInfo> allUserInfo = userMapper.queryUserAll();
        allUserInfo.forEach(info->LogUtil.info("{}", info));
        JUserInfo jUserInfo = new JUserInfo();
        jUserInfo.setCreateDate(new Date());
        jUserInfo.setPassword("123");
        jUserInfo.setUserId(allUserInfo.size() + 1);
        jUserInfo.setUserName("test");
        userMapper.insertUser(jUserInfo);

        JUserInfo selectUserInfo = userMapper.queryUserById((long) allUserInfo.size() + 1);
        LogUtil.info("{}", selectUserInfo);
    }

    private static void testConfig()
    {
        JServerConfig jServerConfig = ConfigComponent.getConfig(JServerConfig.class);
        LogUtil.info("{}", jServerConfig);
    }
}
