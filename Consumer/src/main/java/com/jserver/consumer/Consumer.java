package com.jserver.consumer;

import java.io.Serializable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sictiy.common.services.ConsumerService;
import com.sictiy.common.services.FunctionService;
import com.sictiy.common.services.HelloService;
import com.sictiy.common.util.LogUtil;

/**
 * 调用者
 *
 * @author sictiy.xu
 * @version 2019/12/10 18:17
 **/
public class Consumer implements Serializable
{
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"./consumer.xml"});
        context.start();
        HelloService helloService1 = context.getBean(HelloService.class);
        LogUtil.info("{}", helloService1.sayHello("sictiy"));
        ConsumerService consumerService = context.getBean(ConsumerService.class);
        consumerService.accept(ConsumerService.testConsumer2, "Test");
        FunctionService functionService = context.getBean(FunctionService.class);
        LogUtil.info("{}", functionService.apply(FunctionService.echoFunction, "sictiy"));
    }
}
