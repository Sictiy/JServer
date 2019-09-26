package com.sictiy.jserver.util;

/**
 * @author sictiy.xu
 * @version 2019/09/26 12:01
 **/
public class StringUtil
{
    public static String[] splitString(String src, String regex)
    {
        if (src == null || src.isEmpty())
        {
            return new String[0];
        }
        return src.split(regex);
    }
}
