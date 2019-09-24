package com.sictiy.jserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.db.mapper.JUserMapper;
import com.sictiy.jserver.db.pojo.JUserInfo;
import com.sictiy.jserver.util.LogUtil;

public class Test
{
    public static void main(String[] args) throws IOException
    {
        testDb();
    }

    private static void testDb()
    {
        try
        {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            JUserMapper userMapper = sqlSession.getMapper(JUserMapper.class);

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
        catch (IOException e)
        {
            LogUtil.error("", e);
        }
    }

    private static void testConfig()
    {
        JServerConfig jServerConfig = ConfigComponent.getConfig(JServerConfig.class);
        LogUtil.info("{}", jServerConfig);
    }
}
