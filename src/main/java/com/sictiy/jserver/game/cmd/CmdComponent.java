package com.sictiy.jserver.game.cmd;

/**
 * @author sictiy.xu
 * @version 2019/09/25 15:06
 **/
public class CmdComponent
{
    public static void init()
    {
        // load all Cmd
    }

    public static AbstractCmd getCmdByCode(short code)
    {
        return new AbstractCmd();
    }

}
