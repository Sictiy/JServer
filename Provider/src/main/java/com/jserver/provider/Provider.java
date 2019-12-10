package com.jserver.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * main
 *
 * @author sictiy.xu
 * @version 2019/12/10 17:52
 **/
public class Provider
{
    public static void main(String[] args) throws IOException
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
        context.start();
        System.in.read();
    }
}
