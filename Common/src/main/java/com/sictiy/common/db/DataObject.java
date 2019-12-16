package com.sictiy.common.db;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import com.sictiy.common.entry.type.DataOptionType;

/**
 * 数据库实体
 *
 * @author sictiy.xu
 * @version 2019/12/16 19:52
 **/
@Setter
@Getter
public class DataObject
{
    protected int optionType;

    public DataObject()
    {
        optionType = DataOptionType.NONE;
    }

    @SneakyThrows
    public static <T extends DataObject> T newDataObject(Class<T> clazz)
    {
        var instance = clazz.getDeclaredConstructor().newInstance();
        instance.setOptionType(DataOptionType.INSERT);
        return instance;
    }
}
