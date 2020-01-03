package com.sictiy.common.entry.bean;

import com.sictiy.common.entry.annotation.CommomAnnotation;

/**
 * @author sictiy.xu
 * @version 2020/01/03 17:38
 **/
public interface BeanInterface
{
    default String getFileName()
    {
        var annotation = this.getClass().getAnnotation(CommomAnnotation.class);
        if (annotation != null)
        {
            return annotation.str();
        }
        return "";
    }

    int getId();
}
