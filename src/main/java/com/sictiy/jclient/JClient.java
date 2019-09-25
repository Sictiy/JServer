package com.sictiy.jclient;

import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.net.JConnect;
import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.net.NetComponent;

/**
 * @author sictiy.xu
 * @version 2019/09/25 10:13
 **/
public class JClient
{
    public static void main(String[] args) throws InterruptedException
    {
        JConnect jConnect = NetComponent.getConnection(ConfigComponent.getConfig(JServerConfig.class));
        JMessage jMessage = new JMessage();
        jMessage.setUserId(1001);
        jMessage.setCode((short) 11);
        jMessage.setString("hello");
        for (int i = 0; i < 5; i++)
        {
            Thread.sleep(500);
            jConnect.send(jMessage);
        }
        jConnect.close();
    }
}
