package com.sictiy.common.entry.services;

import com.sictiy.common.entry.annotation.CommomAnnotation;

/**
 * @author sictiy.xu
 * @version 2019/12/10 17:43
 **/
@CommomAnnotation(str = "helloService")
public interface HelloService
{
    String sayHello(String name);
}
