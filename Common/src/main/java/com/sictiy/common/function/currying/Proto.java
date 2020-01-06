package com.sictiy.common.function.currying;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author sictiy.xu
 * @version 2020/01/06 6:58
 **/
public interface Proto<T>
{
    List<T> getList();

    Consumer<List<T>> getConsumer();

    @SuppressWarnings("unchecked")
    default Proto<T> handle(T... param)
    {
        List<T> paramList = this.getList();
        if (param == null || param.length <= 0)
        {
            getConsumer().accept(paramList);
            return null;
        }
        else
        {
            paramList.addAll(Arrays.asList(param));
            return this;
        }
    }

    @SuppressWarnings("unchecked")
    default Proto<T> handle(Consumer<List<T>> consumer, T... param)
    {
        List<T> paramList = this.getList();
        if (param == null || param.length <= 0)
        {
            consumer.accept(paramList);
            return null;
        }
        else
        {
            paramList.addAll(Arrays.asList(param));
            return this;
        }
    }
}
