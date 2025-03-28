package com.artur114.ultradiarrheanote.common.temporallyeffects;

import com.artur114.ultradiarrheanote.common.temporallyeffects.client.ClientTemporallyEffectsEventsManager;
import com.artur114.ultradiarrheanote.common.temporallyeffects.server.ServerTemporallyEffectsEventsManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class TemporallyEffectsEventsHandler {
    public static final ServerTemporallyEffectsEventsManager SERVER_MANAGER = new ServerTemporallyEffectsEventsManager();
    public static final ClientTemporallyEffectsEventsManager CLIENT_MANAGER = new ClientTemporallyEffectsEventsManager();

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking e) {
        if (!e.getTarget().world.isRemote) SERVER_MANAGER.playerEventStartTracking(e);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent e) {
        if ( e.getEntityLiving().world.isRemote) CLIENT_MANAGER.livingEventLivingUpdateEvent(e);
        if (!e.getEntityLiving().world.isRemote) SERVER_MANAGER.livingEventLivingUpdateEvent(e);
    }

    @SubscribeEvent
    public static void attachCapabilitiesLiving(AttachCapabilitiesEvent<Entity> e) {
        if ( e.getObject().world.isRemote) CLIENT_MANAGER.attachCapabilitiesEventEntityLivingBase(e);
        if (!e.getObject().world.isRemote) SERVER_MANAGER.attachCapabilitiesEventEntityLivingBase(e);
    }
}
