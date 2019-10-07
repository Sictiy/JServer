package com.sictiy.jclient;

import java.io.Console;
import java.util.Scanner;

import com.google.flatbuffers.FlatBufferBuilder;
import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.entry.buffer.ChatMsg;
import com.sictiy.jserver.entry.buffer.LoginReq;
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
    private static Scanner scanner;
    private static final boolean USE_CONSOLE = false;
    private static JClientConnect jConnect;

    public static void main(String[] args) throws InterruptedException
    {
        jConnect = NetComponent.getConnection(ConfigComponent.getConfig(JServerConfig.class));
        while (true)
        {
            String input = getInput("cmd:", false);
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
            case "connect":
                newConnect();
                break;
            default:
                break;
        }
    }

    private static void newConnect()
    {
        if (jConnect != null && jConnect.isActive())
        {
            jConnect.close();
        }
        jConnect = NetComponent.getConnection(ConfigComponent.getConfig(JServerConfig.class));
    }

    private static void sendRegister(String[] strings)
    {
        FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();
        String userName = getInput("name:", false);
        String password = getInput("password:", true);
        int userNameOffset = bufferBuilder.createString(userName);
        int passwordOffset = bufferBuilder.createString(password);
        bufferBuilder.finish(RegisterReq.createRegisterReq(bufferBuilder, userNameOffset, passwordOffset));
        jConnect.send(CmdType.REGISTER_REQ, bufferBuilder);
    }

    private static void sendLogin(String[] strings)
    {
        FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();
        String userName = getInput("name:", false);
        String password = getInput("password:", true);
        int userNameOffset = bufferBuilder.createString(userName);
        int passwordOffset = bufferBuilder.createString(password);
        bufferBuilder.finish(LoginReq.createLoginReq(bufferBuilder, userNameOffset, passwordOffset));
        jConnect.send(CmdType.LOGIN_REQ, bufferBuilder);
    }

    private static void sendChat(String[] strings)
    {
        FlatBufferBuilder bufferBuilder = new FlatBufferBuilder();
        int msgOffset = bufferBuilder.createString(strings[1]);
        int type = Integer.parseInt(strings[2]);
        long target = Long.parseLong(strings[3]);
        bufferBuilder.finish(ChatMsg.createChatMsg(bufferBuilder, type, target, msgOffset));
        jConnect.send(CmdType.CHAT_REQ, bufferBuilder);
    }

    private static String getInput(String tips, boolean isPassword)
    {
        if (USE_CONSOLE)
        {
            if (console == null)
            {
                console = System.console();
            }
            if (isPassword)
            {
                return new String(console.readPassword(tips));
            }
            else
            {
                return console.readLine(tips);
            }
        }
        else
        {
            if (scanner == null)
            {
                scanner = new Scanner(System.in);
            }
            System.out.println(tips);
            return scanner.nextLine();
        }
    }
}
