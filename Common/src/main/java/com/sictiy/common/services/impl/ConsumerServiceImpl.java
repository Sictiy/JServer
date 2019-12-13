package com.sictiy.common.entry.services.impl;

import com.sictiy.common.entry.function.SerializableConsumer;
import com.sictiy.common.entry.services.ConsumerService;
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
