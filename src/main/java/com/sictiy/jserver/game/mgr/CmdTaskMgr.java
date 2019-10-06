package com.sictiy.jserver.game.mgr;

import java.util.ArrayList;
import java.util.List;

import com.sictiy.jserver.entry.executor.JExecutor;
import com.sictiy.jserver.entry.executor.TaskQueue;
import com.sictiy.jserver.game.cmd.CmdTask;

/**
 * @author sictiy.xu
 * @version 2019/09/25 17:26
 **/
public class CmdTaskMgr
{
    private static TaskQueue<CmdTask> commonQueue;
    private static List<TaskQueue<CmdTask>> playerQueues;

    public static boolean init()
    {
        // init cmd task queue
        JExecutor executor = new JExecutor("cmdTaskExecutor");
        commonQueue = new TaskQueue<>(executor);
        playerQueues = new ArrayList<>();
        for (int i = 0; i < executor.getMaxSize(); i++)
        {
            playerQueues.add(new TaskQueue<>(executor));
        }
        return true;
    }

    public static void addCmdTask(CmdTask cmdTask)
    {
        long playerId = cmdTask.getMessage().getUserId();
        if (playerId < 0)
        {
            commonQueue.submit(cmdTask);
        }
        else
        {
            playerQueues.get((int) (playerId % playerQueues.size())).submit(cmdTask);
        }
    }
}
