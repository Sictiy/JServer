package com.sictiy.jserver.entry.hooker;

/**
 * @author sictiy.xu
 * @version 2019/10/11 12:27
 **/
public class JShutDownHooker extends Thread
{
    private IServer iServer;

    public JShutDownHooker(IServer iServer)
    {
        this.iServer = iServer;
    }

    @Override
    public void run()
    {
        iServer.onShutDown();
    }
}
