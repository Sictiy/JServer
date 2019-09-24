package com.sictiy.jserver.entry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sictiy.xu
 * @version 2019/09/24 17:35
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommomAnnotation
{
    int id() default 0;
    String str() default "";
}
