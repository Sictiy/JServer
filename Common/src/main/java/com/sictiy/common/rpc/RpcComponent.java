package com.sictiy.common.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sictiy.common.entry.annotation.CommomAnnotation;
import com.sictiy.common.util.LogUtil;
import com.sictiy.processor.single.SingleInstance;

/**
 * rpc组件
 *
 * @author sictiy.xu
 * @version 2019/12/11 10:17
 **/
@SingleInstance
public class RpcComponent
{
    private ClassPathXmlApplicationContext context;

    public boolean init(String... config)
    {
        try
        {
            context = new ClassPathXmlApplicationContext(config);
            context.start();
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
            return false;
        }
        return true;
    }

    public ClassPathXmlApplicationContext getContext()
    {
        return context;
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> clazz)
    {
        if (context == null)
        {
            return null;
        }
        CommomAnnotation annotation = clazz.getAnnotation(CommomAnnotation.class);
        try
        {
            if (annotation == null)
            {
                return context.getBean(clazz);
            }
            return (T) context.getBean(annotation.str());
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
