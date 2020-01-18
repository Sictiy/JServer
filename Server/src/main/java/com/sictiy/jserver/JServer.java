package com.sictiy.jserver;

import com.sictiy.common.config.ConfigComponent;
import com.sictiy.common.config.xml.JServerConfig;
import com.sictiy.common.db.DbComponent;
import com.sictiy.common.hooker.IServer;
import com.sictiy.common.hooker.JShutDownHooker;
import com.sictiy.common.net.JServerChannelInitializer;
import com.sictiy.common.net.NetComponent;
import com.sictiy.common.rpc.RpcComponent;
import com.sictiy.common.scheduler.SchedulerComponent;
import com.sictiy.common.template.TempComponent;
import com.sictiy.common.util.LogUtil;
import com.sictiy.jserver.game.cmd.CmdComponent;
import com.sictiy.jserver.game.mgr.GameMgrComponent;
import com.sictiy.jserver.game.player.module.PlayerModuleComponent;
import com.sictiy.jserver.net.ServerCmdHandler;

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
        doRun(GameMgrComponent.getInstance()::stop, "gameMgr stop");
        doRun(SchedulerComponent.getInstance()::stop, "scheduler stop");
        doRun(NetComponent.getInstance()::stop, "net stop");
    }

    @Override
    public void start()
    {
        // cmd组件
        doRun(CmdComponent.getInstance()::init, "cmd init");
        // 玩家module
        doRun(PlayerModuleComponent.getInstance()::init, "player module init");
        // 数据模板组件
        doRun(TempComponent.getInstance()::init, "template init");
        // 数据库组件
        doRun(DbComponent.getInstance()::init, "db init");
        // 游戏Mgr组件
        doRun(GameMgrComponent.getInstance()::init, "gameMgr init");
        // rpc组件
        doRun(() -> RpcComponent.getInstance().init("provider.xml"), "rpc init");
        // 调度任务组件
        doRun(SchedulerComponent.getInstance()::init, "scheduler init");
        // 网络组件
        NetComponent.getInstance().set(ConfigComponent.getInstance().getConfig(JServerConfig.class).getPort(), new JServerChannelInitializer(new ServerCmdHandler()));
        doRun(NetComponent.getInstance()::init, "net init");
        LogUtil.info("{} start successful", this.getClass());
    }
}
