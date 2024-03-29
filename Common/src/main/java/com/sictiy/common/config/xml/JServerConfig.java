package com.sictiy.common.config.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sictiy.common.entry.annotation.CommomAnnotation;


/**
 * @author sictiy.xu
 * @version 2019/09/24 15:19
 **/
@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "serverConfig")
@XmlType(propOrder = {
        "name",
        "port",
        "id",
        "address"
})
@CommomAnnotation(str = "jServerConfig")
public class JServerConfig implements Serializable
{
    private String name;

    private int id;
    
    private int port;

    private String address;

    public JServerConfig()
    {
    }
}

