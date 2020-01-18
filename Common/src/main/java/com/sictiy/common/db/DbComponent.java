package com.sictiy.common.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sictiy.common.entry.type.DataOptionType;
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
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        catch (IOException e)
        {
            LogUtil.error("", e);
            return false;
        }
        return true;
    }

    public <T extends MapperInterface<? extends DataObject>> T getMapper(Class<T> clazz)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession.getMapper(clazz);
    }

    public <T extends MapperInterface<R>, R extends DataObject> void insertOrUpdateBatch(Collection<R> objects, Class<T> mapperClass)
    {
        var mapper = getMapper(mapperClass);
        objects.forEach(object -> {
            if (object.getOptionType() == DataOptionType.INSERT)
            {
                mapper.insert(object);
            }
            else
            {
                mapper.update(object);
            }
        });
    }

    public <T extends MapperInterface<R>, R extends DataObject> void insertOrUpdate(R object, Class<T> mapperClass)
    {
        if (object.getOptionType() == DataOptionType.INSERT)
        {
            getMapper(mapperClass).insert(object);
        }
        else
        {
            getMapper(mapperClass).update(object);
        }
    }
}
