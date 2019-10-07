package com.sictiy.jserver.net;

import com.sictiy.jserver.game.cmd.AbstractCmd;
import com.sictiy.jserver.game.cmd.CmdComponent;
import com.sictiy.jserver.game.cmd.CmdTask;
import com.sictiy.jserver.game.mgr.CmdTaskMgr;
import com.sictiy.jserver.util.LogUtil;

public class CmdHandler
{
    public static void handlerCmdMessage(AbstractConnect abstractConnect, JMessage message)
    {
        AbstractCmd cmd = CmdComponent.getCmdByCode(message.getCode());
        if (cmd == null)
        {
            LogUtil.error("cmd code id error, code: {}", message.getCode());
            return;
        }
        CmdTask cmdTask = new CmdTask();
        cmdTask.setCmd(cmd);
        cmdTask.setConnect(abstractConnect);
        cmdTask.setMessage(message);
        CmdTaskMgr.addCmdTask(cmdTask);
    }
}
