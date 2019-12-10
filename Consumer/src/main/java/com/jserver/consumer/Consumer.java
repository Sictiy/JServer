package com.jserver.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sictiy.common.entry.services.HelloService;

/**
 * 调用者
 *
 * @author sictiy.xu
 * @version 2019/12/10 18:17
 **/
public class Consumer
{
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();
        HelloService helloService = (HelloService) context.getBean("helloService");
        System.out.println(helloService.sayHello("sictiy"));
    }
}
