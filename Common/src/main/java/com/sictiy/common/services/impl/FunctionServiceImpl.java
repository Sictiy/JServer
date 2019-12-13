package com.sictiy.common.services.impl;

import com.sictiy.common.entry.function.SerializableFunction;
import com.sictiy.common.services.FunctionService;
import com.sictiy.common.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/12/13 14:34
 **/
public class FunctionServiceImpl implements FunctionService
{
    @Override
    public <T, R> R apply(SerializableFunction<T, R> function, T t)
    {
        R result = null;
        try
        {
            result = function.apply(t);
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
        }
        LogUtil.info("function:{}", t);
        return result;
    }
}
