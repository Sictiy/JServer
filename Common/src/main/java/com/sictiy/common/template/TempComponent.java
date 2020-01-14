package com.sictiy.common.template;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sictiy.common.template.bean.BeanInterface;
import com.sictiy.common.util.ClassUtil;
import com.sictiy.processor.single.SingleInstance;

/**
 * @author sictiy.xu
 * @version 2019/09/25 11:16
 **/
@SingleInstance
public class TempComponent
{
    private Map<Class<? extends BeanInterface>, AbstractTempDataMgr<? extends BeanInterface>> tempMgrMap;

    @SuppressWarnings("unchecked")
    public boolean init()
    {
        tempMgrMap = new ConcurrentHashMap<>();
        var mgrList = ClassUtil.getImplClassByAbstractClass(AbstractTempDataMgr.class);
        mgrList.forEach(aClass -> {
            try
            {
                var instance = aClass.getDeclaredConstructor().newInstance();
                instance.init();
                tempMgrMap.put(instance.getTClass(), instance);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T extends BeanInterface> T getBeanById(Class<T> beanClass, int id)
    {
        if (!tempMgrMap.containsKey(beanClass))
        {
            return null;
        }
        return (T) tempMgrMap.get(beanClass).getBeanById(id);
    }
}
