package com.sictiy.common.services.impl;

import com.sictiy.common.function.function.SerializableConsumer;
import com.sictiy.common.services.ConsumerService;
import com.sictiy.common.util.LogUtil;

/**
 * 消费者接口
 *
 * @author sictiy.xu
 * @version 2019/12/13 9:42
 **/
public class ConsumerServiceImpl implements ConsumerService
{
    @Override
    public <T> void accept(SerializableConsumer<T> consumer, T t)
    {
        try
        {
            consumer.accept(t);
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
        }
        LogUtil.info("consumer:{}", t);
    }
}
