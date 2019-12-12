package com.sictiy.jserver.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.sictiy.common.entry.annotation.JobAnnotation;
import com.sictiy.common.util.ClassUtil;
import com.sictiy.common.util.LogUtil;
import com.sictiy.common.util.TimeUtil;
import com.sictiy.processor.single.SingleInstance;

/**
 * 任务调度组件
 *
 * @author sictiy.xu
 * @version 2019/12/11 12:11
 **/
@SingleInstance
public class SchedulerComponent
{
    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;
    private final String GROUP = "quartz";

    public boolean init()
    {
        schedulerFactory = new StdSchedulerFactory();
        try
        {
            scheduler = schedulerFactory.getScheduler();
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
            return false;
        }
        return addAllJob();
    }

    private boolean addAllJob()
    {
        var allJob = ClassUtil.getImplClassByAbstractClass(AbstractSchedulerJob.class);
        for (Class<AbstractSchedulerJob> job : allJob)
        {
            JobAnnotation annotation = job.getAnnotation(JobAnnotation.class);
            if (annotation != null)
            {
                boolean success;
                if (annotation.repeatInterval() > 0)
                {
                    // 简单间隔任务
                    int delay = 0;
                    if (annotation.wholePoint())
                    {
                        int total = TimeUtil.getCurrentDaySeconds();
                        delay = annotation.repeatInterval() - total % annotation.repeatInterval();
                    }
                    success = addJob(annotation.name(), job, delay, annotation.count(), annotation.repeatInterval());
                }
                else
                {
                    // cron任务
                    success = addJob(annotation.name(), job, annotation.cron());
                }
                if (!success)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void stop()
    {
        if (scheduler != null)
        {
            try
            {
                scheduler.shutdown();
            }
            catch (SchedulerException e)
            {
                LogUtil.exception(e);
            }
        }
    }

    public boolean addJob(String name, Class<? extends Job> jobClass, String cron)
    {
        JobDetail detail = JobBuilder.newJob(jobClass).withIdentity(name, GROUP).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, GROUP).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
        try
        {
            scheduler.scheduleJob(detail, trigger);
            scheduler.start();
        }
        catch (SchedulerException e)
        {
            LogUtil.exception(e);
            return false;
        }
        return true;
    }

    public boolean addJob(String name, Class<? extends Job> jobClass, int delay, int repeat, int interval)
    {
        JobDetail detail = JobBuilder.newJob(jobClass).withIdentity(name, GROUP).build();
        var triggerBuilder = TriggerBuilder.newTrigger().withIdentity(name, GROUP);
        if (delay > 0)
        {
            triggerBuilder.startAt(TimeUtil.getSecondsAfterDate(delay));
        }
        else
        {
            triggerBuilder.startNow();
        }
        if (repeat > 0)
        {
            triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(repeat, interval));
        }
        else
        {
            triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(interval));
        }
        var trigger = triggerBuilder.build();
        try
        {
            scheduler.scheduleJob(detail, trigger);
            scheduler.start();
        }
        catch (SchedulerException e)
        {
            LogUtil.exception(e);
            return false;
        }
        return true;
    }
}
