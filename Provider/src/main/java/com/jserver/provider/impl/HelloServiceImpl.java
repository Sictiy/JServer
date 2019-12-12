package com.jserver.provider.impl;

import com.sictiy.common.entry.services.HelloService;

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
        System.out.println("return: " + result + this.toString());
        return result;
    }
}
