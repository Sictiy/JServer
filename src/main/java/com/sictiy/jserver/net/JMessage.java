package com.sictiy.jserver.net;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sictiy.xu
 * @version 2019/09/24 11:58
 **/
@Getter
@Setter
public class JMessage
{
    short length;

    short code;

    long userId;

    byte[] data;
}
