package com.sictiy.common.function.functor;

import java.util.function.Function;

/**
 * 函子
 *
 * @author sictiy.xu
 * @version 2020/01/06 10:03
 **/
public interface Functor<T, F extends Functor<?, ?>>
{
    <R> F map(Function<T, R> f);
}
