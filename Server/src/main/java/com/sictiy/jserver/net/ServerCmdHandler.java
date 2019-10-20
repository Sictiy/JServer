package com.sictiy.jserver.net;

import com.sictiy.common.net.AbstractConnect;
import com.sictiy.common.net.ICmdHandler;
import com.sictiy.common.net.JMessage;
import com.sictiy.common.util.LogUtil;
import com.sictiy.jserver.game.cmd.AbstractCmd;
import com.sictiy.jserver.game.cmd.CmdComponent;
import com.sictiy.jserver.game.cmd.CmdTask;
import com.sictiy.jserver.game.mgr.CmdTaskMgr;

/**
 * @author 10460
 * @version 2019/10/19 11:49
 **/
public class ServerCmdHandler implements ICmdHandler
{
    @Override
    public void handlerCmdMessage(AbstractConnect abstractConnect, JMessage message)
    {
        AbstractCmd cmd = CmdComponent.getInstance().getCmdByCode(message.getCode());
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
