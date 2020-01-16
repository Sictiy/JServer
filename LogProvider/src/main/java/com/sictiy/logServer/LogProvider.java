package com.sictiy.logServer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sictiy.common.rpc.RpcComponent;
import com.sictiy.common.util.LogUtil;

/**
 * 日志提供者
 *
 * @author sictiy.xu
 * @version 2019/12/24 14:18
 **/
public class LogProvider
{
    public static void main(String[] args)
    {
        RpcComponent.getInstance().init("log-provider.xml");
        LogUtil.info("start log provider successful!");
        while (true)
        {
        }
    }
}
