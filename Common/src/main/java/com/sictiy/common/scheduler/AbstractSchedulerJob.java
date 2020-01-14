package com.sictiy.common.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.sictiy.common.util.LogUtil;

/**
 * 抽象任务
 *
 * @author sictiy.xu
 * @version 2019/12/11 14:36
 **/
public abstract class AbstractSchedulerJob implements Job
{
    @Override
    public void execute(JobExecutionContext jobExecutionContext)
    {
        try
        {
            execute();
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
        }
    }

    public abstract void execute();
}
