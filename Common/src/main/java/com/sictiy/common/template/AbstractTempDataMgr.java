package com.sictiy.common.template;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sictiy.common.config.ConfigComponent;
import com.sictiy.common.template.bean.BeanInterface;
import com.sictiy.common.util.CsvUtil;

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
        beanMap = new ConcurrentHashMap<>();
        String csvPath = ConfigComponent.getInstance().RESOURCE_DIR + "csv\\";
        var beanList = CsvUtil.getBeanList(getTClass(), csvPath);
        beanList.forEach(bean -> {
            beanMap.put(bean.getId(), bean);
        });
        return true;
    }

    public T getBeanById(int id)
    {
        return beanMap.get(id);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getTClass()
    {
        if (tClass == null)
        {
            tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return tClass;
    }
}
