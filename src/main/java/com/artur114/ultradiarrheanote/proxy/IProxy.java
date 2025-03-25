package com.artur114.ultradiarrheanote.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public interface IProxy {
    void preInit(FMLPreInitializationEvent e);
    void init(FMLInitializationEvent e);
    void postInit(FMLPostInitializationEvent e);
    default void serverStarting(FMLServerStartingEvent e) {}
    default void registerItemRenderer(Item item, int meta, String id) {}
}
