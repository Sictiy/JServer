package com.sictiy.jserver.config;

import java.util.HashMap;
import java.util.Map;

import com.sictiy.jserver.config.xml.JServerConfig;
import com.sictiy.jserver.entry.annotation.CommomAnnotation;
import com.sictiy.jserver.util.XmlUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/24 12:26
 **/
public class ConfigComponent
{
    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String CONFIG_DIR = USER_DIR + "/src/main/resources/config/";

    private static Map<Class, Object> configMap = new HashMap<>();

    public static String getConfigDir()
    {
        return CONFIG_DIR;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getConfig(Class<T> clazz)
    {
        if (!configMap.containsKey(clazz))
        {
            CommomAnnotation annotation = clazz.getAnnotation(CommomAnnotation.class);
            T config = XmlUtil.convertXmlToObject(clazz, getConfigDir() + annotation.str() + ".xml");
            configMap.put(clazz, config);
            return config;
        }
        return (T) configMap.get(clazz);
    }
}
