package com.sictiy.common.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.sictiy.common.entry.annotation.CommomAnnotation;
import com.sictiy.common.function.function.SerializableConsumer;
import com.sictiy.common.util.LogUtil;

/**
 * 消费者接口
 *
 * @author sictiy.xu
 * @version 2019/12/13 9:40
 **/
@CommomAnnotation(str = "consumerService")
@Service
public interface ConsumerService
{
    <T> void accept(SerializableConsumer<T> consumer, T t);

    SerializableConsumer<String> testConsumer1 = (SerializableConsumer<String>) s -> LogUtil.info("test consumer1:{}", s);

    // 一定要new 为啥？
    SerializableConsumer<String> testConsumer2 = new SerializableConsumer<String>()
    {
        @Override
        public void accept(String s)
        {
            LogUtil.info("test consumer2:{}", s);
        }
    };
}
