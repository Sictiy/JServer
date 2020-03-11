package com.sictiy.plugin.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.util.RecursionGuard;
import com.intellij.openapi.util.RecursionManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.augment.PsiAugmentProvider;
import com.intellij.psi.impl.source.PsiExtensibleClass;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.sictiy.plugin.processor.Processor;
import com.sictiy.plugin.processor.ProcessorComponent;

/**
 * @author sictiy.xu
 * @version 2020/03/10 15:12
 **/
public class MyPsiAugmentProvider extends PsiAugmentProvider
{
    @NotNull
    @Override
    protected <Psi extends PsiElement> List<Psi> getAugments(@NotNull PsiElement element, @NotNull Class<Psi> type)
    {
        final List<Psi> emptyResult = Collections.emptyList();
        if ((type != PsiClass.class && type != PsiField.class && type != PsiMethod.class) || !(element instanceof PsiExtensibleClass))
        {
            return emptyResult;
        }
        final PsiClass psiClass = (PsiClass) element;
        if (psiClass.isAnnotationType() || psiClass.isInterface())
        {
            return emptyResult;
        }
        final List<Psi> cachedValue;
        if (type == PsiField.class)
        {
            cachedValue = CachedValuesManager.getCachedValue(element, new FieldCachedValueProvider<>(type, psiClass));
            //            cachedValue = CachedValuesManager.getCachedValue(element, new MyCachedValueProvider<>(type, psiClass, "field"));
        }
        else if (type == PsiMethod.class)
        {
            cachedValue = CachedValuesManager.getCachedValue(element, new MethodCachedValueProvider<>(type, psiClass));
            //            cachedValue = CachedValuesManager.getCachedValue(element, new MyCachedValueProvider<>(type, psiClass, "method"));
        }
        else
        {
            cachedValue = CachedValuesManager.getCachedValue(element, new ClassCachedValueProvider<>(type, psiClass));
            //            cachedValue = CachedValuesManager.getCachedValue(element, new MyCachedValueProvider<>(type, psiClass, "class"));
        }
        return null != cachedValue ? cachedValue : emptyResult;
    }

    private static class FieldCachedValueProvider<Psi extends PsiElement> extends MyCachedValueProvider<Psi>
    {
        private static final RecursionGuard<PsiClass> ourGuard = RecursionManager.createGuard("field");

        FieldCachedValueProvider(Class<Psi> type, PsiClass psiClass)
        {
            super(type, psiClass, ourGuard);
        }
    }

    private static class MethodCachedValueProvider<Psi extends PsiElement> extends MyCachedValueProvider<Psi>
    {
        private static final RecursionGuard<PsiClass> ourGuard = RecursionManager.createGuard("method");

        MethodCachedValueProvider(Class<Psi> type, PsiClass psiClass)
        {
            super(type, psiClass, ourGuard);
        }
    }

    private static class ClassCachedValueProvider<Psi extends PsiElement> extends MyCachedValueProvider<Psi>
    {
        private static final RecursionGuard<PsiClass> ourGuard = RecursionManager.createGuard("class");

        ClassCachedValueProvider(Class<Psi> type, PsiClass psiClass)
        {
            super(type, psiClass, ourGuard);
        }
    }

    private static class MyCachedValueProvider<Psi extends PsiElement> implements CachedValueProvider<List<Psi>>
    {
        private final Class<Psi> type;
        private final PsiClass psiClass;
        private final RecursionGuard<PsiClass> recursionGuard;

        private MyCachedValueProvider(Class<Psi> type, PsiClass psiClass, String guardName)
        {
            this.type = type;
            this.psiClass = psiClass;
            this.recursionGuard = RecursionManager.createGuard(guardName);
        }

        private MyCachedValueProvider(Class<Psi> type, PsiClass psiClass, RecursionGuard<PsiClass> recursionGuard)
        {
            this.type = type;
            this.psiClass = psiClass;
            this.recursionGuard = recursionGuard;
        }

        @Nullable
        @Override
        public Result<List<Psi>> compute()
        {
            return recursionGuard.doPreventingRecursion(psiClass, true, () -> Result.create(getPsis(psiClass, type), psiClass));
        }

        // 根据传入的psi元素， 由相应的处理器 处理后 将结果返回
        private static <Psi extends PsiElement> List<Psi> getPsis(PsiClass psiClass, Class<Psi> type)
        {
            final List<Psi> result = new ArrayList<>();
            final Collection<Processor> processors = ProcessorComponent.getInstance(psiClass.getProject()).getClassProcessors(type);
            for (Processor processor : processors)
            {
                final List<? super PsiElement> elements = processor.process(psiClass);
                elements.forEach(element -> result.add((Psi) element));
            }
            return result;
        }
    }
}
