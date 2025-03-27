package com.artur114.ultradiarrheanote.common.temporallyeffects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public class TemporallyEffectBase implements ITemporallyEffect {
    protected int duration;

    public TemporallyEffectBase() {}

    public TemporallyEffectBase(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean update(EntityLivingBase entity) {
        if (this.duration > 0) {
            this.duration--;
        }
        return this.duration > 0;
    }

    @Override
    public void onStartTick() {}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.duration = nbt.getInteger("duration");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setString("className", this.getClass().getName());
        nbt.setInteger("duration", duration);
        return nbt;
    }
}
