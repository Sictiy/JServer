package com.sictiy.jserver.game.cmd;

import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.net.JServerConnect;

/**
 *
 * @author sictiy.xu
 * @version 2019/09/25 14:55
 **/
public abstract class AbstractCmd
{
    public abstract void execute(JServerConnect connect, JMessage jMessage);
}
