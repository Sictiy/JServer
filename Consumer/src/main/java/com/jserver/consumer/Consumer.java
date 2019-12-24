package com.jserver.consumer;

import java.io.Serializable;

import com.sictiy.common.rpc.RpcComponent;
import com.sictiy.common.rpc.services.ConsumerService;
import com.sictiy.common.rpc.services.FunctionService;
import com.sictiy.common.rpc.services.HelloService;
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
        RpcComponent.getInstance().init("services/consumer.xml");
        HelloService helloService1 = RpcComponent.getInstance().getService(HelloService.class);
        LogUtil.info("{}", helloService1.sayHello("sictiy"));
        ConsumerService consumerService = RpcComponent.getInstance().getService(ConsumerService.class);
        consumerService.accept(ConsumerService.testConsumer2, "Test");
        FunctionService functionService = RpcComponent.getInstance().getService(FunctionService.class);
        LogUtil.info("{}", functionService.apply(FunctionService.echoFunction, "sictiy"));
        LogUtil.warn("test log!");
    }
}
