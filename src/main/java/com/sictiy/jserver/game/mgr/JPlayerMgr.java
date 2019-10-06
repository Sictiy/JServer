package com.sictiy.jserver.game.mgr;

import java.util.Map;

import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.game.player.module.impl.UserInfoModule;
import com.sictiy.jserver.net.JServerConnect;
import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/25 14:47
 **/
public class JPlayerMgr
{
    private static Map<Long, JPlayer> allPlayerMap;
    private static Map<String, JPlayer> allPlayerNameMap;

    public static boolean init()
    {
        return true;
    }

    public static JPlayer register(JServerConnect connect, String name, String password)
    {
        if (allPlayerNameMap.containsKey(name))
        {
            LogUtil.error("name is contains");
            return null;
        }
        JPlayer player = new JPlayer();
        player.getPlayerModule(UserInfoModule.class).register(name, password);
        allPlayerNameMap.put(name, player);
        allPlayerMap.put(player.getUserId(), player);
        return player;
    }
}
