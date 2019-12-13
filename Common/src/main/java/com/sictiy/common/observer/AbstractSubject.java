package com.sictiy.common.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象主题
 *
 * @author sictiy.xu
 * @version 2019/12/13 16:54
 **/
public class AbstractSubject implements Subject
{
    private Map<Integer, List<Observer>> observersMap = new ConcurrentHashMap<>();

    public void subscribe(int eventType, Observer observer)
    {
        observersMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(observer);
    }

    public void unsubscribe(int eventType, Observer observer)
    {
        observersMap.getOrDefault(eventType, Collections.emptyList()).remove(observer);
    }

    @Override
    public void notifyEvent(int eventType, Object... objects)
    {
        observersMap.getOrDefault(eventType, Collections.emptyList()).forEach(observer -> observer.update(objects));
    }
}
