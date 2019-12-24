package com.sictiy.common.rpc.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.sictiy.common.entry.annotation.CommomAnnotation;
import com.sictiy.common.function.function.SerializableFunction;

/**
 * function接口
 *
 * @author sictiy.xu
 * @version 2019/12/13 14:27
 **/
@CommomAnnotation(str = "functionService")
@Service
public interface FunctionService
{
    <T, R> R apply(SerializableFunction<T, R> function, T t);

    SerializableFunction<String, String> echoFunction = new SerializableFunction<String, String>()
    {
        @Override
        public String apply(String s)
        {
            return "echo " + s;
        }
    };
}
