package com.sictiy.common.entry.executor;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author sictiy.xu
 * @version 2019/09/25 15:58
 **/
@Setter
@Getter
public class DelayedTask<T extends Runnable> implements Delayed
{
    private T task;

    private long delayTime;

    public DelayedTask(T task, long delayTime)
    {
        this.task = task;
        this.delayTime = delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit)
    {
        return unit.convert(delayTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o)
    {
        long diff = delayTime - ((DelayedTask) o).getDelayTime();
        if (diff > 0)
        {
            return 1;
        }
        else if (diff < 0)
        {
            return -1;
        }
        return 0;
    }
}
