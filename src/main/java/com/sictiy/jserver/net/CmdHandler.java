package com.sictiy.jserver.net;

import com.sictiy.jserver.game.cmd.AbstractCmd;
import com.sictiy.jserver.game.cmd.CmdComponent;
import com.sictiy.jserver.game.cmd.CmdTask;
import com.sictiy.jserver.game.mgr.CmdTaskMgr;

public class CmdHandler
{
    public static void handlerCmdMessage(AbstractConnect abstractConnect, JMessage message)
    {
        AbstractCmd cmd = CmdComponent.getCmdByCode(message.getCode());
        CmdTask cmdTask = new CmdTask();
        cmdTask.setCmd(cmd);
        cmdTask.setConnect(abstractConnect);
        cmdTask.setMessage(message);
        CmdTaskMgr.addCmdTask(cmdTask);
    }
}
