package com.artur114.ultradiarrheanote.common.temporallyeffects;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class TemporallyEffectsEventsHandler {
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {

    }
}
