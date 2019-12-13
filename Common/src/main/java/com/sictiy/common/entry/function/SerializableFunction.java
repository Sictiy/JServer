package com.sictiy.common.entry.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author sictiy.xu
 * @version 2019/12/13 14:26
 **/
@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable
{
}
