package com.artur114.ultradiarrheanote.common.temporallyeffects.server;

import com.artur114.ultradiarrheanote.common.temporallyeffects.ITemporallyEffect;
import com.artur114.ultradiarrheanote.common.temporallyeffects.ITemporallyEffectsManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IServerTemporallyEffectsManager extends ITemporallyEffectsManager, INBTSerializable<NBTTagCompound> {
    void syncToClient(EntityPlayerMP clientIn);
    void addEffect(ITemporallyEffect effect);

    @Override
    default boolean isRemote() {return false;}
}
