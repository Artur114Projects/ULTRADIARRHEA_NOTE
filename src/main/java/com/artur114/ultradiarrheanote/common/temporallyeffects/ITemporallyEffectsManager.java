package com.artur114.ultradiarrheanote.common.temporallyeffects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ITickable;

public interface ITemporallyEffectsManager extends ITickable {
    boolean isRemote();
    EntityLivingBase getEntity();
    boolean isEmpty();
}
