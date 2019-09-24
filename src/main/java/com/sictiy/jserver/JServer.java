package com.sictiy.jserver;

import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.net.JServerChannelInitializer;
import com.sictiy.jserver.net.NetComponent;

/**
 * @author sictiy.xu
 * @version 2019/09/24 10:40
 **/
public class JServer
{
    public static void main(String[] args)
    {
        start();
    }

    public static void start()
    {
        // 数据模板组件
        // 数据库组件
        // 网络组件
        NetComponent.start(ConfigComponent.getConfig(JServerConfig.class).getPort(), new JServerChannelInitializer());
    }
}
