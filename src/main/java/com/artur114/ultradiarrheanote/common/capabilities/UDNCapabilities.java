package com.artur114.ultradiarrheanote.common.capabilities;

import com.artur114.ultradiarrheanote.common.temporallyeffects.ITemporallyEffectsManager;
import com.artur114.ultradiarrheanote.common.temporallyeffects.server.IServerTemporallyEffectsManager;
import com.artur114.ultradiarrheanote.common.temporallyeffects.server.ServerTemporallyEffectsManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;


public class UDNCapabilities {

    @CapabilityInject(ITemporallyEffectsManager.class)
    public static final Capability<ITemporallyEffectsManager> TEMPORALLY_EFFECTS_MANAGER = null;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(ITemporallyEffectsManager.class, new Capability.IStorage<ITemporallyEffectsManager>() {
            @Override
            public NBTBase writeNBT(Capability<ITemporallyEffectsManager> capability, ITemporallyEffectsManager instance, EnumFacing side) {
                if (!(instance instanceof IServerTemporallyEffectsManager)) {
                    return null;
                }

                return ((IServerTemporallyEffectsManager) instance).serializeNBT();
            }

            @Override
            public void readNBT(Capability<ITemporallyEffectsManager> capability, ITemporallyEffectsManager instance, EnumFacing side, NBTBase nbt) {
                if (!(instance instanceof IServerTemporallyEffectsManager) || !(nbt instanceof NBTTagCompound)) {
                    return;
                }

                ((IServerTemporallyEffectsManager) instance).deserializeNBT((NBTTagCompound) nbt);
            }
        }, () -> new ServerTemporallyEffectsManager(null));

    }
}
