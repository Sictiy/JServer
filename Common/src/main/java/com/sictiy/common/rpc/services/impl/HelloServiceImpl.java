package com.sictiy.common.rpc.services.impl;

import com.sictiy.common.rpc.services.HelloService;

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
        return result;
    }
}
