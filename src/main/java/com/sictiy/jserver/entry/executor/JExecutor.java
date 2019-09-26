package com.sictiy.jserver.entry.executor;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sictiy.jserver.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/25 16:09
 **/
@Setter
@Getter
public class JExecutor
{
    private String executorName;
    private ExecutorService executor;
    private int maxSize;

    public JExecutor(String executorName)
    {
        this(executorName, Runtime.getRuntime().availableProcessors() * 4);
    }

    public JExecutor(String executorName, int maxSize)
    {
        this.maxSize = maxSize;
        this.executorName = executorName;
        executor = Executors.newFixedThreadPool(maxSize, (Runnable r) -> {
            Thread thread = new Thread(r, executorName);
            thread.setDaemon(false);
            thread.setUncaughtExceptionHandler((t, throwable) -> LogUtil.error("Uncaught Exception In Thread: " + executorName, throwable));
            return thread;
        });
    }

    public void submit(Runnable runnable)
    {
        try
        {
            executor.submit(runnable);
        }
        catch (Exception e)
        {
            LogUtil.error("execute task error!", e);
        }
    }

    public void stop()
    {
        executor.shutdown();
    }
}
