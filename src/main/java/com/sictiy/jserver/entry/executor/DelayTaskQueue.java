package com.sictiy.jserver.entry.executor;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author sictiy.xu
 * @version 2019/09/25 15:57
 **/
public class DelayTaskQueue
{
    private DelayQueue<DelayedTask> taskDelayQueue;
    private JExecutor executor;
    private boolean isRun;

    public DelayTaskQueue(JExecutor executor)
    {
        this.executor = executor;
        this.taskDelayQueue = new DelayQueue<>();
        isRun = true;
        executor.submit(this::execute);
    }

    public void stop()
    {
        isRun = false;
    }

    private void execute()
    {
        while (isRun)
        {
            try
            {
                DelayedTask delayedTask = taskDelayQueue.take();
                Runnable task = delayedTask.getTask();
                executor.submit(task);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public DelayedTask submit(Runnable task, long time, TimeUnit timeUnit)
    {
        long timeout = TimeUnit.NANOSECONDS.convert(time, timeUnit);
        DelayedTask delayedTask = new DelayedTask<>(task, timeout);
        taskDelayQueue.put(delayedTask);
        return delayedTask;
    }

    public boolean remove(DelayedTask delayedTask)
    {
        return taskDelayQueue.remove(delayedTask);
    }
}
