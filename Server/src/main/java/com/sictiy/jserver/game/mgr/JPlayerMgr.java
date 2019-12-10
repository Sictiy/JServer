package com.sictiy.jserver.game.mgr;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sictiy.common.entry.type.CmdType;
import com.sictiy.common.entry.type.UniqueType;
import com.sictiy.common.net.JServerConnect;
import com.sictiy.common.util.FlatBufferUtil;
import com.sictiy.common.util.LogUtil;
import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.db.mapper.JUserMapper;
import com.sictiy.jserver.db.pojo.JUserInfo;
import com.sictiy.jserver.game.player.JPlayer;
import com.sictiy.jserver.game.player.module.impl.UserInfoModule;

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
        allPlayerMap = new ConcurrentHashMap<>();
        allPlayerNameMap = new ConcurrentHashMap<>();
        return true;
    }

    public static boolean register(JServerConnect connect, String name, String password)
    {
        var user = DbComponent.getInstance().getMapper(JUserMapper.class).queryJUserByUserName(name);
        if (user != null)
        {
            connect.send(CmdType.ERROR, FlatBufferUtil.newCommonMsgBuilder("name is exist!"));
            return false;
        }
        var userInfo = new JUserInfo();
        userInfo.setUserName(name);
        userInfo.setPassword(password);
        userInfo.setCreateDate(new Date());
        userInfo.setUserId(UniqueMgr.getUnique(UniqueType.USER));
        DbComponent.getInstance().getMapper(JUserMapper.class).insertJUser(userInfo);
        return true;
    }

    public static JPlayer login(JServerConnect connect, String name, String password)
    {
        if (allPlayerNameMap.containsKey(name))
        {
            LogUtil.error("player is onLine");
            return null;
        }
        var userInfo = DbComponent.getInstance().getMapper(JUserMapper.class).queryJUserByUserName(name);
        if (userInfo == null || !userInfo.getPassword().equals(password))
        {
            connect.send(CmdType.ERROR, FlatBufferUtil.newCommonMsgBuilder("password error or account do not exist!"));
            return null;
        }
        return onPlayerLogin(connect, userInfo);
    }

    private static JPlayer onPlayerLogin(JServerConnect connect, JUserInfo userInfo)
    {
        JPlayer player = new JPlayer();
        player.setConnect(connect);
        player.getPlayerModule(UserInfoModule.class).setUserInfo(userInfo);
        player.login();
        allPlayerNameMap.put(userInfo.getUserName(), player);
        allPlayerMap.put(player.getUserId(), player);
        return player;
    }

    public static JPlayer getPlayerById(long userId)
    {
        return allPlayerMap.get(userId);
    }

    public static Collection<JPlayer> getAllPlayer()
    {
        return allPlayerMap.values();
    }

    public static void stop()
    {
        allPlayerMap.values().forEach(JPlayer::onLogout);
        allPlayerMap.clear();
    }
}
