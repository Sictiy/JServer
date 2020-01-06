package com.sictiy.common.executor;

import java.util.concurrent.LinkedBlockingQueue;

import com.sictiy.common.util.LogUtil;


/**
 * @author sictiy.xu
 * @version 2019/09/25 17:06
 **/
public class TaskQueue<T extends Runnable>
{
    private JExecutor executor;
    private LinkedBlockingQueue<T> queue;

    public TaskQueue(JExecutor executor)
    {
        this.executor = executor;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void submit(T task)
    {
        if (!queue.offer(task))
        {
            LogUtil.error("queue is max!");
            return;
        }

        while (queue.size() >= 1)
        {
            Runnable runnable = queue.poll();
            if (runnable != null)
            {
                executor.submit(runnable);
            }
        }
    }
}
