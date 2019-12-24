package com.sictiy.common.rpc.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.sictiy.common.entry.annotation.CommomAnnotation;

/**
 * @author sictiy.xu
 * @version 2019/12/10 17:43
 **/
@CommomAnnotation(str = "helloService")
@Service
public interface HelloService
{
    String sayHello(String name);
}
