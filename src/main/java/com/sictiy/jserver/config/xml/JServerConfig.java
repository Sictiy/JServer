package com.sictiy.jserver.config.xml;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sictiy.jserver.entry.annotation.CommomAnnotation;

/**
 * @author sictiy.xu
 * @version 2019/09/24 15:19
 **/
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "serverConfig")
@XmlType(propOrder = {
        "name",
        "port",
        "id"
})
@CommomAnnotation(str = "jServerConfig")
public class JServerConfig implements Serializable
{
    private String name;

    private int id;
    
    private int port;

    public JServerConfig()
    {
    }

    @Override
    public String toString()
    {
        return String.format("name:%s, id:%d, port:%d", name, id, port);
    }
}

