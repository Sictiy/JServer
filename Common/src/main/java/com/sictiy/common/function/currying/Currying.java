package com.sictiy.common.function.currying;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.sictiy.common.util.LogUtil;

/**
 * 柯里化测试
 *
 * @author sictiy.xu
 * @version 2020/01/06 6:58
 **/
public class Currying<T> implements Proto<T>
{
    List<T> paramList = null;
    Consumer<List<T>> consumer = null;

    @Override
    public List<T> getList()
    {
        return paramList;
    }

    @Override
    public Consumer<List<T>> getConsumer()
    {
        return consumer;
    }

    public Currying(Consumer<List<T>> consumer)
    {
        this.paramList = new ArrayList<>();
        this.consumer = consumer;
    }

    public static void main(String[] args)
    {

        Proto<String> p = new Currying<>(a -> {
            StringBuilder builder = new StringBuilder();
            for (String s : a)
            {
                builder.append(s);
            }
            LogUtil.info("{}", builder.toString());
        });
        p.handle("aa").handle("bb").handle("cc").handle();
        System.out.println("end.....");

        Proto<String> p1 = new Currying<>(a -> LogUtil.info("{}", a));
        p1.handle("dd").handle("ee").handle("ff").handle();
        System.out.println("end.....");
    }
}
