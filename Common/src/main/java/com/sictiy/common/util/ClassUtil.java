package com.sictiy.common.util;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.sictiy.common.entry.type.RegexType;


/**
 * @author sictiy.xu
 * @version 2019/10/07 11:28
 **/
public class ClassUtil
{
    /**
     * 根据父类获取同目包下的实现类
     *
     * @param clazz clazz
     * @return java.util.Set<java.lang.Class < ? extends T>>
     **/
    @SuppressWarnings("unchecked")
    public static <T> Set<Class<T>> getImplClassByAbstractClass(Class<T> clazz)
    {
        Set<Class> classes = getClassByPackage(clazz.getPackageName());
        return classes.stream().filter(clazz::isAssignableFrom).map(aClass -> (Class<T>) aClass).filter(aClass -> !clazz.equals(aClass)).collect(Collectors.toSet());
    }

    public static Set<Class> getClassByPackage(String packageName)
    {
        Set<Class> classes = new HashSet<>();
        try
        {
            String packageDir = packageName.replaceAll(RegexType.DOT, RegexType.SLASH);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            var dirs = classLoader.getResources(packageDir);
            Set<String> classNames = new HashSet<>();
            while (dirs.hasMoreElements())
            {
                URL url = dirs.nextElement();
                if ("file".equals(url.getProtocol()))
                {
                    var filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                    classNames.addAll(findClassNameByDir(filePath, packageName));
                }
                else if ("jar".equals(url.getProtocol()))
                {
                    // 未测试
                    var jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                    var entries = jarFile.entries();
                    while (entries.hasMoreElements())
                    {
                        var entry = entries.nextElement();
                        var entryName = entry.getName();
                        // 如果已/开头，获取后面的字符串
                        if (entryName.charAt(0) == '/')
                        {
                            entryName = entryName.substring(1);
                        }
                        if (!entryName.startsWith(packageDir))
                        {
                            continue;
                        }
                        boolean isPackage = entryName.lastIndexOf('/') != -1;
                        var currentPackageName = packageName;
                        if (isPackage)
                        {
                            currentPackageName = entryName.substring(0, entryName.lastIndexOf('/')).replace(RegexType.SLASH, RegexType.DOT);
                        }
                        if (entryName.endsWith(".class") && !entry.isDirectory())
                        {
                            String className = entryName.substring(currentPackageName.length() + 1, entryName.length() + 6);
                            classNames.add(currentPackageName + '.' + className);
                        }
                    }
                }
            }
            for (String className : classNames)
            {
                classes.add(Class.forName(className));
            }
        }
        catch (Exception e)
        {
            LogUtil.error("", e);
        }
        return classes;
    }

    private static Set<String> findClassNameByDir(String dir, String packageName)
    {
        Set<String> classNames = new HashSet<>();
        File fileDir = new File(dir);
        if (!fileDir.exists() || !fileDir.isDirectory())
        {
            return classNames;
        }
        File[] allFiles = fileDir.listFiles(file -> file.getName().endsWith("class") || file.isDirectory());
        if (allFiles == null)
        {
            return classNames;
        }
        for (File file : allFiles)
        {
            if (file.isDirectory())
            {
                classNames.addAll(findClassNameByDir(file.getAbsolutePath(), packageName + "." + file.getName()));
            }
            else
            {
                String className = file.getName().substring(0, file.getName().length() - 6);
                classNames.add(packageName + "." + className);
            }
        }
        return classNames;
    }
}
