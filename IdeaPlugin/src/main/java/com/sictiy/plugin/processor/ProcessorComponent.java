package com.sictiy.plugin.processor;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;

/**
 * 项目级服务组件 维护当前所有的处理器实例
 *
 * @author sictiy.xu
 * @version 2019/10/15 17:39
 **/
public class ProcessorComponent
{
    private final Map<Class<? extends PsiElement>, Collection<Processor>> classProcessorMap;
    private final Map<String, Collection<Processor>> annotationProcessorMap;
    private final Collection<String> allAnnotationNames;
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
        allAnnotationNames = ConcurrentHashMap.newKeySet();
    }

    private void init()
    {
        initProcessors();
        inited = true;
    }

    private void initProcessors()
    {
        // 初始化所有processor
        classProcessorMap.clear();
        annotationProcessorMap.clear();
        allAnnotationNames.clear();
        for (Processor processor : ProcessorManager.getAllProcessors())
        {
            if (!processor.isEnable(propertiesComponent))
            {
                continue;
            }
            Class<? extends Annotation>[] supportedAnnotations = processor.getSupportedAnnotations();
            for (Class<? extends Annotation> annotation : supportedAnnotations)
            {
                annotationProcessorMap.computeIfAbsent(annotation.getName(), k -> ConcurrentHashMap.newKeySet()).add(processor);
                annotationProcessorMap.computeIfAbsent(annotation.getSimpleName(), k -> ConcurrentHashMap.newKeySet()).add(processor);
            }
            classProcessorMap.computeIfAbsent(processor.getSupportedClass(), k -> ConcurrentHashMap.newKeySet()).add(processor);
        }
        allAnnotationNames.addAll(annotationProcessorMap.keySet());
    }

    /**
     * 获取所有支持改类的处理器
     *
     * @param supportedClass supportedClass
     * @return java.util.Collection<com.sictiy.plugin.processor.Processor>
     **/
    public Collection<Processor> getClassProcessors(@NotNull Class<? extends PsiElement> supportedClass)
    {
        return classProcessorMap.computeIfAbsent(supportedClass, k -> ConcurrentHashMap.newKeySet());
    }

    /**
     * 获取注解对应的处理器
     *
     * @param psiAnnotation psiAnnotation
     * @return java.util.Collection<com.sictiy.plugin.processor.Processor>
     **/
    public Collection<Processor> getAnnotationProcessors(@NotNull PsiAnnotation psiAnnotation)
    {
        final String name = psiAnnotation.getQualifiedName();
        final Collection<Processor> result = name == null ? null : annotationProcessorMap.get(name);
        return result == null ? Collections.emptySet() : result;
    }
}
