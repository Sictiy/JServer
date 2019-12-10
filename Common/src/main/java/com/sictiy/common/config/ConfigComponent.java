package com.sictiy.common.config;

import java.util.HashMap;
import java.util.Map;

import com.sictiy.common.entry.annotation.CommomAnnotation;
import com.sictiy.common.util.XmlUtil;
import com.sictiy.processor.single.SingleInstance;


/**
 * @author sictiy.xu
 * @version 2019/09/24 12:26
 **/
@SingleInstance
public class ConfigComponent
{
    private final String USER_DIR = System.getProperty("user.dir");

    public final String RESOURCE_DIR = USER_DIR + "\\Resources\\";

    private final String CONFIG_DIR = RESOURCE_DIR + "config\\";

    private Map<Class, Object> configMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getConfig(Class<T> clazz)
    {
        if (!configMap.containsKey(clazz))
        {
            CommomAnnotation annotation = clazz.getAnnotation(CommomAnnotation.class);
            T config = XmlUtil.convertXmlToObject(clazz, CONFIG_DIR + annotation.str() + ".xml");
            configMap.put(clazz, config);
            return config;
        }
        return (T) configMap.get(clazz);
    }

    public boolean init()
    {
        return true;
    }
}
