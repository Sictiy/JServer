package com.sictiy.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理
 *
 * @author sictiy.xu
 * @version 2019/12/11 17:26
 **/
public class TimeUtil
{
    public static final int MINUTE = 60;

    public static final int HOUR = MINUTE * 60;

    public static final int DAY = HOUR * 24;

    /**
     * 获取当天秒数
     *
     * @return int
     **/
    public static int getCurrentDaySeconds()
    {
        return (int) (getCurrentTimeMillis() - getOverZeroCalendar().getTimeInMillis()) / 1000;
    }

    public static long getCurrentTimeMillis()
    {
        return System.currentTimeMillis();
    }

    public static Calendar getOverZeroCalendar()
    {
        return getOverZeroCalendar(new Date());
    }

    public static Date getSecondsAfterDate(int seconds)
    {
        Date date = new Date();
        date.setTime(date.getTime() + seconds * 1000);
        return date;
    }

    public static Calendar getOverZeroCalendar(Date date)
    {
        Calendar calendarOverZero = Calendar.getInstance();
        calendarOverZero.setTime(date);
        calendarOverZero.set(Calendar.HOUR_OF_DAY, 0);
        calendarOverZero.set(Calendar.MINUTE, 0);
        calendarOverZero.set(Calendar.SECOND, 0);
        calendarOverZero.set(Calendar.MILLISECOND, 0);
        return calendarOverZero;
    }

    public static void main(String[] args)
    {
        LogUtil.error("{}", getOverZeroCalendar().getTime());
        LogUtil.error("{}", getCurrentDaySeconds());
        LogUtil.error("{}", new Date());
        LogUtil.error("{}", getSecondsAfterDate(60));
        LogUtil.error("{}", getSecondsAfterDate(600));
    }
}
