package com.artur114.ultradiarrheanote.common.network;

import com.artur114.ultradiarrheanote.common.misc.AddDiarrheaTask;
import com.artur114.ultradiarrheanote.common.events.ServerEventsHandler;
import com.artur114.ultradiarrheanote.common.item.ItemUltraDiarrheaNote;
import com.artur114.ultradiarrheanote.main.MainUDN;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerPacketSyncDiarrheaNote implements IMessage {
    private NBTTagCompound nbt;

    public ServerPacketSyncDiarrheaNote() {}

    public ServerPacketSyncDiarrheaNote(NBTTagCompound nbt) {
        this.nbt = nbt;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class HandlerSDN implements IMessageHandler<ServerPacketSyncDiarrheaNote, IMessage> {

        @Override
        public IMessage onMessage(ServerPacketSyncDiarrheaNote message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            ItemStack stack = player.getHeldItem(EnumHand.values()[message.nbt.getInteger("hand")]);

            if (!stack.isEmpty() && stack.getItem() instanceof ItemUltraDiarrheaNote) {
                if (message.nbt.hasKey("kill")) {
                    ServerEventsHandler.TIMER_TASKS_MANAGER.addTask(new AddDiarrheaTask(player.world, player.getPosition(), message.nbt.getString("kill")));
                }
                if (message.nbt.hasKey("openState")) {
                    stack.getOrCreateSubCompound(MainUDN.MODID).setBoolean("open", message.nbt.getBoolean("openState"));
                }
                if (message.nbt.hasKey("syncData")) {
                    stack.getOrCreateSubCompound(MainUDN.MODID).setTag("data", message.nbt.getCompoundTag("syncData"));
                }
            }

            return null;
        }
    }

    public static NBTTagCompound getBaseNBT(EnumHand hand) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("hand", hand.ordinal());
        return nbt;
    }
}
