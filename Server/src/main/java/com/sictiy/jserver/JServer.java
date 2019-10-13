package com.sictiy.jserver;

import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.entry.hooker.IServer;
import com.sictiy.jserver.entry.hooker.JShutDownHooker;
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
public class JServer implements IServer
{
    private static final JServer jServer = new JServer();

    public static void main(String[] args)
    {
        Runtime.getRuntime().addShutdownHook(new JShutDownHooker(jServer));
        jServer.start();
    }

    @Override
    public void onShutDown()
    {

    }

    @Override
    public void start()
    {
        // cmd组件
        CmdComponent.getInstance().init();
        // 玩家module
        PlayerModuleComponent.getInstance().init();
        // 数据模板组件
        TempComponent.getInstance().init();
        // 数据库组件
        DbComponent.getInstance().init();
        // 游戏Mgr组件
        GameMgrComponent.getInstance().init();
        // 网络组件
        NetComponent.start(ConfigComponent.getConfig(JServerConfig.class).getPort(), new JServerChannelInitializer());

    }
}
