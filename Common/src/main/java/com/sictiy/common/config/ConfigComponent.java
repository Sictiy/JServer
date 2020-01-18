package com.sictiy.common.config;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;

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

    public final String RESOURCE_DIR = USER_DIR + "\\..\\Resources\\";

    private Map<Class, Object> configMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public <T> T getConfig(Class<T> clazz)
    {
        if (!configMap.containsKey(clazz))
        {
            CommomAnnotation annotation = clazz.getAnnotation(CommomAnnotation.class);

            InputStream inputStream = Resources.getResourceAsStream("config/jServerConfig.xml");

            T config = XmlUtil.convertXmlToObject(clazz, inputStream);
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
