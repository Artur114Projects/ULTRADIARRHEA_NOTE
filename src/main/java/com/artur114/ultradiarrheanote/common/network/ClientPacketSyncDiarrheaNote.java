package com.artur114.ultradiarrheanote.common.network;

import com.artur114.ultradiarrheanote.common.events.ServerEventsHandler;
import com.artur114.ultradiarrheanote.common.item.ItemUltraDiarrheaNote;
import com.artur114.ultradiarrheanote.common.misc.AddDiarrheaTask;
import com.artur114.ultradiarrheanote.common.network.base.NBTPacketBase;
import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientPacketSyncDiarrheaNote extends NBTPacketBase {
    public ClientPacketSyncDiarrheaNote() {}

    public ClientPacketSyncDiarrheaNote(NBTTagCompound nbt) {
        super(nbt);
    }

    public static class HandlerCDN implements IMessageHandler<ClientPacketSyncDiarrheaNote, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(ClientPacketSyncDiarrheaNote message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = Minecraft.getMinecraft().player;
                EnumHand hand = EnumHand.values()[message.nbt.getInteger("hand")];
                ItemStack stack = player.getHeldItem(hand);

                if (!stack.isEmpty() && stack.getItem() instanceof ItemUltraDiarrheaNote) {
                    if (message.nbt.hasKey("syncData")) {
                        NBTTagCompound stackData = stack.getOrCreateSubCompound(MainUDN.MODID);
                        NBTTagCompound syncData = message.nbt.getCompoundTag("syncData");

                        stackData.setTag("immutableData", syncData.getCompoundTag("immutableData"));
                        stackData.setTag("data", stackData.getCompoundTag("data"));
                    }
                    if (message.nbt.hasKey("openGui")) {
                        ((ItemUltraDiarrheaNote) stack.getItem()).openBook(stack);
                        ((ItemUltraDiarrheaNote) stack.getItem()).openGui(hand, message.nbt.getCompoundTag("openGui").getBoolean("mutableInputLine"));
                    }
                }
            });
            return null;
        }
    }

    public static NBTTagCompound getBaseNBT(EnumHand hand) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("hand", hand.ordinal());
        return nbt;
    }
}
