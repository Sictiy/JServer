package com.sictiy.jserver.template;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sictiy.common.config.ConfigComponent;
import com.sictiy.common.entry.bean.BeanInterface;
import com.sictiy.common.util.CsvUtil;
import com.sictiy.common.util.LogUtil;

/**
 * 抽象模板数据管理
 *
 * @author sictiy.xu
 * @version 2020/01/03 17:28
 **/
abstract public class AbstractTempDataMgr<T extends BeanInterface>
{
    private volatile Map<Integer, T> beanMap;

    protected Class<T> tClass;

    public boolean init()
    {
        return reload();
    }

    public boolean reload()
    {
        try
        {
            for (var type : this.getClass().getDeclaredFields())
            {
                LogUtil.info("{}", type);
            }
            Type type = this.getClass().getDeclaredField("tClass").getGenericType();
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        beanMap = new ConcurrentHashMap<>();
        String csvPath = ConfigComponent.getInstance().RESOURCE_DIR + "csv\\";
        var beanList = CsvUtil.getBeanList(tClass, csvPath);
        beanList.forEach(bean -> {
            beanMap.put(bean.getId(), bean);
        });
        return true;
    }

    public T getBeanById(int id)
    {
        return beanMap.get(id);
    }

    public Class<T> getTClass()
    {
        return tClass;
    }
}
