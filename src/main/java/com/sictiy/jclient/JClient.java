package com.sictiy.jclient;

import java.io.Console;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.entry.buffer.RegisterReq;
import com.sictiy.jserver.entry.type.CmdType;
import com.sictiy.jserver.entry.type.RegexType;
import com.sictiy.jserver.net.JClientConnect;
import com.sictiy.jserver.net.NetComponent;
import com.sictiy.jserver.util.StringUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/25 10:13
 **/
public class JClient
{
    private static Console console;
    private static JClientConnect jConnect;

    public static void main(String[] args) throws InterruptedException
    {
        jConnect = NetComponent.getConnection(ConfigComponent.getConfig(JServerConfig.class));
        console = System.console();
        while (true)
        {
            String input = console.readLine("send:");
            if (!doCmd(input))
            {
                break;
            }
        }
        jConnect.close();
    }

    private static boolean doCmd(String cmdString)
    {
        String[] cmdStrings = StringUtil.splitString(cmdString, RegexType.BLANK_SPACE);
        if (cmdString.length() <= 0)
        {
            return false;
        }
        doCmdByType(cmdStrings);
        return true;
    }

    private static void doCmdByType(String[] strings)
    {
        switch (strings[0])
        {
            case "register":
                sendRegister(strings);
                break;
            case "login":
                sendLogin(strings);
                break;
            case "chat":
                sendChat(strings);
                break;
            default:
                break;
        }
    }

    private static void sendRegister(String[] strings)
    {
        FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();
        String userName = console.readLine("name:");
        String password = console.readLine("password:");
        int userNameOffset = bufferBuilder.createString(userName);
        int passwordOffset = bufferBuilder.createString(password);
        bufferBuilder.finish(RegisterReq.createRegisterReq(bufferBuilder, userNameOffset, passwordOffset));
        jConnect.send(CmdType.REGISTER_REQ, bufferBuilder);
    }

    private static void sendLogin(String[] strings)
    {

    }

    private static void sendChat(String[] strings)
    {

    }
}
