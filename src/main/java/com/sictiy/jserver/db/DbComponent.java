package com.sictiy.jserver.db;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/25 9:28
 **/
public class DbComponent
{
    private static SqlSessionFactory sqlSessionFactory;

    public static void init()
    {
        try
        {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        catch (IOException e)
        {
            LogUtil.error("", e);
        }
    }

    public static <T> T getMapper(Class<T> clazz)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession.getMapper(clazz);
    }
}
