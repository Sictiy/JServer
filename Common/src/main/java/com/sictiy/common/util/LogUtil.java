package com.sictiy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author sictiy.xu
 * @version 2019/09/24 15:38
 **/
public class LogUtil
{
    public static Logger log = LoggerFactory.getLogger(LogUtil.class);

    public static void info(String string, Object ... objects)
    {
        log.info(string, objects);
    }

    public static void info(String string, Throwable throwable)
    {
        log.info(string, throwable);
    }

    public static void error(String string, Object ... objects)
    {
        log.error(string, objects);
    }

    public static void error(String string, Throwable throwable)
    {
        log.error(string, throwable);
    }

    public static void exception(Throwable throwable)
    {
        log.error("", throwable);
    }

    public static void warn(String string, Object ... objects)
    {
        log.warn(string, objects);
    }

    public static void warn(String string, Throwable throwable)
    {
        log.warn(string, throwable);
    }
}
