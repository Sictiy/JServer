package com.sictiy.jserver.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.sictiy.jserver.config.ConfigComponent;
import com.sictiy.jserver.config.xml.JServerConfig;

/**
 * @author sictiy.xu
 * @version 2019/09/24 15:03
 **/
public class XmlUtil
{
    public static void convertToXml(Object object, String path)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            FileWriter fileWriter = new FileWriter(path);
            marshaller.marshal(object, fileWriter);
        }
        catch (JAXBException | IOException e)
        {
            LogUtil.error("", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertXmlToObject(Class<T> t, String path)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(t);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fileReader = new FileReader(path);
            return (T) unmarshaller.unmarshal(fileReader);
        }
        catch (JAXBException | FileNotFoundException | ClassCastException e)
        {
            LogUtil.error("", e);
        }
        return null;
    }
}
