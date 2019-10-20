package com.sictiy.common.net;

import lombok.Getter;
import lombok.Setter;

import com.sictiy.common.entry.component.AbstractServerComponent;


/**
 * @author sictiy.xu
 * @version 2019/09/26 14:34
 **/
@Setter
@Getter
public class JServerConnect extends AbstractConnect
{
    private IOnwer owner;
}
