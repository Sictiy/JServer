package com.sictiy.common.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.sictiy.common.entry.annotation.CommomAnnotation;
import com.sictiy.common.template.bean.BeanInterface;

/**
 * csv工具
 *
 * @author sictiy.xu
 * @version 2020/01/03 17:06
 **/
public class CsvUtil
{
    public static <T extends BeanInterface> List<T> getBeanList(Class<T> tClass, String csvPath)
    {
        List<T> result = new ArrayList<>();
        try
        {
            var annotation = tClass.getAnnotation(CommomAnnotation.class);
            if (annotation != null)
            {
                String fileName = annotation.str() + ".csv";
                FileReader fileReader = new FileReader(csvPath + fileName);
                result = new CsvToBeanBuilder<T>(fileReader).withType(tClass).withSkipLines(2).build().parse();
            }
        }
        catch (FileNotFoundException e)
        {
            LogUtil.exception(e);
        }
        return result;
    }
}
