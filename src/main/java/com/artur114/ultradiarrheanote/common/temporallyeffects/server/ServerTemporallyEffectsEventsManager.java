package com.artur114.ultradiarrheanote.common.temporallyeffects.server;

import com.artur114.ultradiarrheanote.common.capabilities.GenericCapabilityProvider;
import com.artur114.ultradiarrheanote.common.capabilities.UDNCapabilities;
import com.artur114.ultradiarrheanote.common.temporallyeffects.ITemporallyEffectsManager;
import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ServerTemporallyEffectsEventsManager {

    public void attachCapabilitiesEventEntityLivingBase(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof EntityLivingBase) e.addCapability(new ResourceLocation(MainUDN.MODID, "temporally_effects_manager"), new GenericCapabilityProvider<>(new ServerTemporallyEffectsManager((EntityLivingBase) e.getObject()), UDNCapabilities.TEMPORALLY_EFFECTS_MANAGER));
    }

    public void livingEventLivingUpdateEvent(LivingEvent.LivingUpdateEvent e) {
        ITemporallyEffectsManager manager = e.getEntityLiving().getCapability(UDNCapabilities.TEMPORALLY_EFFECTS_MANAGER, null);
        if (manager != null && !manager.isEmpty()) manager.update();
    }

    public void playerEventStartTracking(PlayerEvent.StartTracking e) {
        ITemporallyEffectsManager manager = e.getTarget().getCapability(UDNCapabilities.TEMPORALLY_EFFECTS_MANAGER, null);
        if (manager != null && !manager.isEmpty() && !manager.isRemote() && e.getTarget() != e.getEntityPlayer()) ((IServerTemporallyEffectsManager) manager).syncToClient((EntityPlayerMP) e.getEntityPlayer());
    }
}
