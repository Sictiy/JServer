package com.sictiy.common.function.stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.sictiy.common.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2020/01/09 15:49
 **/
public abstract class MyStream<T>
{
    private Spliterator<T> sourceSpliterator;

    private MyStream<T> preStream;

    private static class Head<T> extends MyStream<T>
    {
        @Override
        Consumer<T> warp(Consumer<T> consumer)
        {
            throw new UnsupportedOperationException();
        }

        private Head(Collection<T> source)
        {
            super(source);
        }
    }

    abstract Consumer<T> warp(Consumer<T> consumer) throws Exception;

    private abstract static class StatelessOp<T> extends MyStream<T>
    {
        private StatelessOp(MyStream<T> preStream)
        {
            super(preStream);
        }
    }

    private abstract static class TerminalOp<T, R>
    {
        abstract R evaluate(MyStream<T> stream, Spliterator<T> spliterator);
    }

    private class ForEachOp extends TerminalOp<T, Void> implements Consumer<T>
    {
        private Consumer<T> consumer;

        ForEachOp(Consumer<T> consumer)
        {
            this.consumer = consumer;
        }

        @Override
        Void evaluate(MyStream<T> stream, Spliterator<T> spliterator)
        {
            return stream.warpAndCopyInto(this, stream.getSourceSpliterator()).get();
        }

        @Override
        public void accept(T t)
        {
            consumer.accept(t);
        }

        public Void get()
        {
            return null;
        }
    }

    private MyStream(Collection<T> source)
    {
        sourceSpliterator = source.spliterator();
    }

    private MyStream(MyStream<T> preStream)
    {
        this.preStream = preStream;
    }

    public MyStream<T> map(Function<T, T> function)
    {
        return new StatelessOp<T>(this)
        {
            @Override
            public Consumer<T> warp(Consumer<T> consumer)
            {
                return a -> consumer.accept(function.apply(a));
            }
        };
    }

    public MyStream<T> filter(Predicate<T> predicate)
    {
        return new StatelessOp<T>(this)
        {
            @Override
            Consumer<T> warp(Consumer<T> consumer)
            {
                return a -> {
                    if (predicate.test(a))
                    {
                        consumer.accept(a);
                    }
                };
            }
        };
    }

    public void forEach(Consumer<T> consumer)
    {
        evaluate(new ForEachOp(consumer));
    }

    private <S extends Consumer<T>> S warpAndCopyInto(S consumer, Spliterator<T> spliterator)
    {
        spliterator.forEachRemaining(warpAll(consumer));
        return consumer;
    }

    private Consumer<T> warpAll(Consumer<T> consumer)
    {
        try
        {
            for (MyStream<T> s = this; s.preStream != null; s = s.preStream)
            {
                consumer = s.warp(consumer);
            }
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
        }
        return consumer;
    }

    private <R> R evaluate(TerminalOp<T, R> terminalOp)
    {
        return terminalOp.evaluate(this, getSourceSpliterator());
    }

    private Spliterator<T> getSourceSpliterator()
    {
        if (preStream != null)
        {
            return preStream.getSourceSpliterator();
        }
        return sourceSpliterator;
    }

    public static <T> MyStream<T> myStream(T[] array)
    {
        return new Head<>(Arrays.asList(array));
    }

    public static void main(String[] args)
    {
        Integer[] array = {1, 2, 3, 4, 5, 5, 6, 7, 4, 34, 5, 20, 11, 32, 10, 0};
        MyStream<Integer> myStream = myStream(array);
        myStream.map(a -> a + 1).filter(a -> a > 10).forEach(LogUtil::info);
    }
}
