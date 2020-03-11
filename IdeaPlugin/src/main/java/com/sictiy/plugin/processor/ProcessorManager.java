package com.sictiy.plugin.processor;

import java.util.Arrays;
import java.util.Collection;

import com.intellij.openapi.components.ServiceManager;
import com.sictiy.plugin.processor.impl.SingleInstanceProcessor;

/**
 * @author sictiy.xu
 * @version 2020/03/11 11:02
 **/
public class ProcessorManager
{
    public static Collection<Processor> getAllProcessors()
    {
        return Arrays.asList(ServiceManager.getService(SingleInstanceProcessor.class));
    }
}
