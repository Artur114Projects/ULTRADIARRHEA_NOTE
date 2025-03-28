package com.artur114.ultradiarrheanote.common.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;


public class GenericCapabilityProvider<C> implements ICapabilitySerializable<NBTTagCompound> {
    protected Capability<C> capability;
    protected C instance;

    public GenericCapabilityProvider(C instance, Capability<C> capability) {
        this.capability = capability;
        this.instance = instance;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        capability.readNBT(instance, null, nbt);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) capability.writeNBT(instance, null);
    }

    @Override
    public boolean hasCapability( Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == this.capability;
    }

    @Nullable
    @Override
    public <T> T getCapability( Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == this.capability ? this.capability.cast(instance) : null;
    }
}