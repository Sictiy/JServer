package com.sictiy.common.function.function;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * @author sictiy.xu
 * @version 2019/12/24 12:08
 **/
@FunctionalInterface
public interface SerializableBiConsumer<T, U> extends Serializable, BiConsumer<T, U>
{
}
