package com.sictiy.jserver.game.cmd;

import java.util.Map;

/**
 * @author sictiy.xu
 * @version 2019/09/25 15:06
 **/
public class CmdComponent
{
    private static Map<Integer, AbstractCmd> allCmd;

    public static boolean init()
    {
        // load all Cmd
        return true;
    }

    public static AbstractCmd getCmdByCode(short code)
    {
        return null;
    }
}
