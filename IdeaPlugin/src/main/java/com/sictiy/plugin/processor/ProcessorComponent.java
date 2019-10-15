package com.sictiy.plugin.processor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

/**
 * 项目级服务组件
 *
 * @author sictiy.xu
 * @version 2019/10/15 17:39
 **/
public class ProcessorComponent
{
    private final Map<Class, Collection<Processor>> classProcessorMap;
    private final Map<String, Collection<Processor>> annotationProcessorMap;
    private final PropertiesComponent propertiesComponent;
    private boolean inited = false;

    public static ProcessorComponent getInstance(@NotNull Project project)
    {
        final ProcessorComponent service = ServiceManager.getService(project, ProcessorComponent.class);
        if (!service.inited)
        {
            service.init();
        }
        return service;
    }

    public ProcessorComponent(@NotNull PropertiesComponent component)
    {
        propertiesComponent = component;
        classProcessorMap = new ConcurrentHashMap<>();
        annotationProcessorMap = new ConcurrentHashMap<>();
    }

    private void init()
    {
        inited = true;
    }

    private void initProcessors()
    {
        // 初始化所有processor
    }
}
