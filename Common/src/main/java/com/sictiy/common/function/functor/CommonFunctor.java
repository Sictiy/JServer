package com.sictiy.common.function.functor;

import java.util.function.Function;

import com.sictiy.common.util.LogUtil;

/**
 * 通用函子
 *
 * @author sictiy.xu
 * @version 2020/01/06 10:05
 **/
public class CommonFunctor<T> implements Functor<T, CommonFunctor<?>>
{
    private T value;

    public CommonFunctor(T value)
    {
        this.value = value;
    }

    @Override
    public <R> CommonFunctor<R> map(Function<T, R> f)
    {
        final R result = f.apply(value);
        return new CommonFunctor<>(result);
    }

    public T getValue()
    {
        return value;
    }

    public static void main(String[] args)
    {
        var functor = new CommonFunctor<>(10);
        LogUtil.info("{}", functor.map(a -> a + 1).map(a -> a * 10).getValue());
    }
}
