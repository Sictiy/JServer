package com.sictiy.jserver.entry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sictiy.xu
 * @version 2019/09/25 15:15
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PlayerModuleAnnotation
{
    short type() default 0;

    String desc() default "";
}
