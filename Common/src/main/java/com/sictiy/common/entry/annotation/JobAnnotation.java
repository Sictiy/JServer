package com.sictiy.common.entry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 调度任务
 *
 * @author sictiy.xu
 * @version 2019/12/11 14:33
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JobAnnotation
{
    // 名称
    String name() default "";

    // cron表达式
    String cron() default "";

    // 重复执行间隔 单位s
    int repeatInterval() default 0;

    // 重复次数
    int count() default 0;

    // 是否整点执行
    boolean wholePoint() default true;
}
