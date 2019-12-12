package com.sictiy.jserver.rpc;

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

    public boolean init()
    {
        try
        {
            ClassPathXmlApplicationContext providerContext = new ClassPathXmlApplicationContext(new String[]{"./provider.xml"});
            context = new ClassPathXmlApplicationContext(new String[]{"./consumer.xml"});
            providerContext.start();
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
        CommomAnnotation annotation = clazz.getAnnotation(CommomAnnotation.class);
        if (annotation == null)
        {
            return context.getBean(clazz);
        }
        return (T) context.getBean(annotation.str());
    }
}
