package com.sictiy.jserver.db;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sictiy.common.util.LogUtil;
import com.sictiy.processor.single.SingleInstance;

/**
 * @author sictiy.xu
 * @version 2019/09/25 9:28
 **/
@SingleInstance
public class DbComponent
{
    private SqlSessionFactory sqlSessionFactory;

    public boolean init()
    {
        try
        {
            InputStream inputStream = Resources.getResourceAsStream("./mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        catch (IOException e)
        {
            LogUtil.error("", e);
            return false;
        }
        return true;
    }

    public <T> T getMapper(Class<T> clazz)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession.getMapper(clazz);
    }
}
