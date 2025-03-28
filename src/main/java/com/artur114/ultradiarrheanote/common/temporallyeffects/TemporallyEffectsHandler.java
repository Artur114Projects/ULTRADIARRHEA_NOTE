package com.artur114.ultradiarrheanote.common.temporallyeffects;

import com.artur114.ultradiarrheanote.common.capabilities.UDNCapabilities;
import com.artur114.ultradiarrheanote.common.temporallyeffects.server.IServerTemporallyEffectsManager;
import net.minecraft.entity.EntityLivingBase;

public class TemporallyEffectsHandler {
    public static void addEffectToLiving(EntityLivingBase entity, ITemporallyEffect effect) {
        if (entity.world.isRemote) return;

        ITemporallyEffectsManager manager = entity.getCapability(UDNCapabilities.TEMPORALLY_EFFECTS_MANAGER, null);
        if (manager != null && !manager.isRemote()) ((IServerTemporallyEffectsManager) manager).addEffect(effect);
    }
}
