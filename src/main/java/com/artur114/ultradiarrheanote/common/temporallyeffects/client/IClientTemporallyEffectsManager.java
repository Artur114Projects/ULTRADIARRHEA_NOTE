package com.artur114.ultradiarrheanote.common.temporallyeffects.client;

import com.artur114.ultradiarrheanote.common.temporallyeffects.ITemporallyEffectsManager;
import net.minecraft.nbt.NBTTagCompound;

public interface IClientTemporallyEffectsManager extends ITemporallyEffectsManager {
    void processSyncData(NBTTagCompound data);
    @Override
    default boolean isRemote() {return true;}
}
