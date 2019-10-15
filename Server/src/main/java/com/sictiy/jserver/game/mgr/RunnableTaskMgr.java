package com.sictiy.jserver.game.mgr;

import java.util.concurrent.TimeUnit;

import com.sictiy.common.entry.executor.DelayTaskQueue;
import com.sictiy.common.entry.executor.DelayedTask;
import com.sictiy.common.entry.executor.JExecutor;
import com.sictiy.common.entry.executor.TaskQueue;

/**
 * @author 10460
 * @version 2019/10/04 20:40
 **/
public class RunnableTaskMgr
{
    private static TaskQueue<Runnable> commonQueue;
    private static DelayTaskQueue delayTaskQueue;

    public static boolean init()
    {
        // init runnable task queue
        JExecutor executor = new JExecutor("RunnableTaskExecutor");
        commonQueue = new TaskQueue<>(executor);
        delayTaskQueue = new DelayTaskQueue(executor);
        return true;
    }

    public static DelayedTask addDelayTask(Runnable task, long time, TimeUnit timeUnit)
    {
        return delayTaskQueue.submit(task, time, timeUnit);
    }

    public static boolean removeDelayTask(DelayedTask task)
    {
        return delayTaskQueue.remove(task);
    }

    public static void addTask(Runnable task)
    {
        commonQueue.submit(task);
    }
}
