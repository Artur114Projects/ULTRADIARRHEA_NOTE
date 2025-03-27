package com.artur114.ultradiarrheanote.main;

import com.artur114.ultradiarrheanote.common.events.ServerEventsHandler;
import com.artur114.ultradiarrheanote.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = "ultradiarrheanote", useMetadata = true)
public class MainUDN {


    public static String MODID = "ultradiarrheanote";
    public static final String NAME = "ULTRADIARRHEA NOTE";
    public static final String VERSION = "v1.0.0";
    public static final String CLIENT_PROXY = "com.artur114.ultradiarrheanote.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.artur114.ultradiarrheanote.proxy.ServerProxy";

    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MODID);
    @Mod.Instance
    public static MainUDN INSTANCE;
    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy PROXY;



    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent e) {
        PROXY.preInit(e);
    }

    @Mod.EventHandler
    public static void Init(FMLInitializationEvent e) {
        PROXY.init(e);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent e) {
        PROXY.postInit(e);
    }

    @Mod.EventHandler
    public static void serverStopping(FMLServerStoppingEvent e) {
        PROXY.serverStopping(e);
    }

    @Mod.EventHandler
    public static void serverStarting(FMLServerStartingEvent e) {
        PROXY.serverStarting(e);
    }
}
