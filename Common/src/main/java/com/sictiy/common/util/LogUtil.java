package com.sictiy.common.util;

import java.util.Optional;
import java.util.function.BiConsumer;

import com.sictiy.common.rpc.RpcComponent;
import com.sictiy.common.rpc.services.LogService;
import com.sictiy.common.rpc.services.impl.LogServerImpl;


/**
 * @author sictiy.xu
 * @version 2019/09/24 15:38
 **/
public class LogUtil
{
    private static class ServiceHolder
    {
        private static LogService logService = Optional.ofNullable(RpcComponent.getInstance().getService(LogService.class)).orElse(new LogServerImpl());
    }

    public static LogService getLog()
    {
        return ServiceHolder.logService;
    }

    public static void info(String string, Object... objects)
    {
        log((s, ob) -> getLog().info(s, ob), string, objects);
    }

    public static void info(String string, Throwable throwable)
    {
        log((s, ob) -> getLog().info(s, ob), string, throwable);
    }

    public static void warn(String string, Object... objects)
    {
        log((s, ob) -> getLog().warn(s, ob), string, objects);
    }

    public static void warn(String string, Throwable throwable)
    {
        log((s, ob) -> getLog().warn(s, ob), string, throwable);
    }

    public static void error(String string, Object... objects)
    {
        log((s, ob) -> getLog().error(s, ob), string, objects);
    }

    public static void error(String string, Throwable throwable)
    {
        log((s, ob) -> getLog().error(s, ob), string, throwable);
    }

    public static void exception(Throwable throwable)
    {
        error("", throwable);
    }

    public static void log(BiConsumer<String, Object[]> biConsumer, String string, Object... objects)
    {
        biConsumer.accept(getCaller() + " " + string, objects);
    }

    public static String getCaller()
    {
        return Thread.currentThread().getStackTrace()[4].toString();
    }
}
