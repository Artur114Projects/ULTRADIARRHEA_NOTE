package com.artur114.ultradiarrheanote.common.network;

import com.artur114.ultradiarrheanote.common.capabilities.UDNCapabilities;
import com.artur114.ultradiarrheanote.common.network.base.NBTPacketBase;
import com.artur114.ultradiarrheanote.common.temporallyeffects.ITemporallyEffectsManager;
import com.artur114.ultradiarrheanote.common.temporallyeffects.client.IClientTemporallyEffectsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientPacketSyncTemporallyEffects extends NBTPacketBase {

    public ClientPacketSyncTemporallyEffects() {
    }

    public ClientPacketSyncTemporallyEffects(NBTTagCompound nbt) {
        super(nbt);
    }

    public static class HandlerSTE implements IMessageHandler<ClientPacketSyncTemporallyEffects, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(ClientPacketSyncTemporallyEffects message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                WorldClient world = Minecraft.getMinecraft().world;

                if (world != null && world.provider.getDimension() == message.nbt.getInteger("dimension")) {
                    Entity rawEntity = world.getEntityByID(message.nbt.getInteger("entityId"));
                    if (rawEntity instanceof EntityLivingBase) {
                        ITemporallyEffectsManager manager = ((EntityLivingBase) rawEntity).getCapability(UDNCapabilities.TEMPORALLY_EFFECTS_MANAGER, null);
                        if (manager != null && manager.isRemote()) {
                            ((IClientTemporallyEffectsManager) manager).processSyncData(message.nbt);
                        }
                    }
                }
            });
            return null;
        }
    }
}
