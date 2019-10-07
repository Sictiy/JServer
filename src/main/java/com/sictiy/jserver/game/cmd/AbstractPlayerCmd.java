package com.sictiy.jserver.game.cmd;

import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.net.JServerConnect;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author 10460
 * @version 2019/10/04 19:52
 **/
public abstract class AbstractPlayerCmd extends AbstractCmd
{
    @Override
    public void execute(JServerConnect connect, JMessage jMessage)
    {
        if (connect.getOwner() == null)
        {
            if (jMessage.getUserId() > 0)
            {
                //TODO get player
                JPlayer player = null;
                connect.setOwner(player);
            }
        }
        if (connect.getOwner() != null)
        {
            execute(connect.getOwner(), jMessage);
        }
        else
        {
            LogUtil.error("connect owner is null");
        }
    }

    /**
     * @param player   player
     * @param jMessage jMessage
     **/
    public abstract void execute(JPlayer player, JMessage jMessage);
}