package com.sictiy.common.net;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sictiy.xu
 * @version 2019/09/26 14:34
 **/
@Setter
@Getter
public class JServerConnect extends AbstractConnect
{
    private IOwner owner;

    @Override
    public void onClose()
    {
        if (owner != null)
        {
            owner.onDropLine();
        }
    }
}
