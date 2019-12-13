package com.sictiy.common.services.impl;

import com.sictiy.common.services.HelloService;
import com.sictiy.common.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/12/10 17:43
 **/
public class HelloServiceImpl implements HelloService
{
    @Override
    public String sayHello(String name)
    {
        String result = "hello " + name;
        LogUtil.info("return:{}", result);
        return result;
    }
}
