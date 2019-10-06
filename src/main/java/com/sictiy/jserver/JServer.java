package com.sictiy.jserver;

import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.game.cmd.CmdComponent;
import com.sictiy.jserver.game.mgr.GameMgrComponent;
import com.sictiy.jserver.game.player.module.PlayerModuleComponent;
import com.sictiy.jserver.net.JServerChannelInitializer;
import com.sictiy.jserver.net.NetComponent;
import com.sictiy.jserver.template.TempComponent;

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
        // cmd组件
        CmdComponent.init();
        // 玩家module
        PlayerModuleComponent.init();
        // 数据模板组件
        TempComponent.init();
        // 数据库组件
        DbComponent.init();
        // 游戏Mgr组件
        GameMgrComponent.init();
        // 网络组件
        NetComponent.start(ConfigComponent.getConfig(JServerConfig.class).getPort(), new JServerChannelInitializer());
    }
}
