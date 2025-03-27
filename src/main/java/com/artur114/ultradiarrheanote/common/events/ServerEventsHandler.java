package com.artur114.ultradiarrheanote.common.events;

import com.artur114.ultradiarrheanote.common.events.managers.TimerTasksManager;
import com.artur114.ultradiarrheanote.common.util.data.WorldData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class ServerEventsHandler {
    public static final TimerTasksManager TIMER_TASKS_MANAGER = new TimerTasksManager();

    @SubscribeEvent
    public static void worldSave(WorldEvent.Save e) {
        TIMER_TASKS_MANAGER.worldEventSave(e);
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent e) {
        TIMER_TASKS_MANAGER.tickEventServerTickEvent(e);
    }

    public static void fMLServerStoppingEvent(FMLServerStoppingEvent e) {
        TIMER_TASKS_MANAGER.fMLServerStoppingEvent(e);
    }

    public static void fMLServerStartingEvent(FMLServerStartingEvent e) {
        TIMER_TASKS_MANAGER.fMLServerStartingEvent(e);
    }
}
