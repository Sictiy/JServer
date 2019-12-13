package com.sictiy.common.entry.function;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author sictiy.xu
 * @version 2019/12/13 12:03
 **/
@FunctionalInterface
public interface SerializableConsumer<T> extends Consumer<T>, Serializable
{
}
