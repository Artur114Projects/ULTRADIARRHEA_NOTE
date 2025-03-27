package com.artur114.ultradiarrheanote.proxy;

import com.artur114.ultradiarrheanote.common.events.ServerEventsHandler;
import com.artur114.ultradiarrheanote.register.RegisterHandler;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        RegisterHandler.registerPackets();
    }

    @Override
    public void init(FMLInitializationEvent e) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }

    @Override
    public void serverStarting(FMLServerStartingEvent e) {
        ServerEventsHandler.fMLServerStartingEvent(e);
    }

    @Override
    public void serverStopping(FMLServerStoppingEvent e) {
        ServerEventsHandler.fMLServerStoppingEvent(e);
    }
}
