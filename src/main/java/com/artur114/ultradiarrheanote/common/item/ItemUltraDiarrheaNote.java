package com.artur114.ultradiarrheanote.common.item;

import com.artur114.ultradiarrheanote.client.gui.GuiDiarrheaNote;
import com.artur114.ultradiarrheanote.common.network.ClientPacketSyncDiarrheaNote;
import com.artur114.ultradiarrheanote.common.util.data.UDNConfigs;
import com.artur114.ultradiarrheanote.main.MainUDN;
import com.artur114.ultradiarrheanote.register.IIsNeedRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ItemUltraDiarrheaNote extends BaseItem implements IIsNeedRegister {

    public ItemUltraDiarrheaNote(String name) {
        super(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.COMBAT);

        this.addPropertyOverride(new ResourceLocation("open"), (stack, worldIn, entityIn) -> {
            if (entityIn == null && !stack.isOnItemFrame()) {
                return 0;
            }
            return stack.getOrCreateSubCompound(MainUDN.MODID).getBoolean("open") ? 1 : 0;
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            this.openBook(playerIn.getHeldItem(handIn));
            this.initNBT(playerIn.getHeldItem(handIn));
            this.initClient(playerIn, handIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    private void initClient(EntityPlayer player, EnumHand hand) {
        NBTTagCompound nbt = ClientPacketSyncDiarrheaNote.getBaseNBT(hand);
        NBTTagCompound stackData = player.getHeldItem(hand).getOrCreateSubCompound(MainUDN.MODID);

        NBTTagCompound syncData = new NBTTagCompound();
        syncData.setTag("immutableData", stackData.getCompoundTag("immutableData"));
        syncData.setTag("data", stackData.getCompoundTag("data"));
        nbt.setTag("syncData", stackData);

        NBTTagCompound guiData = new NBTTagCompound();
        guiData.setBoolean("mutableInputLine", UDNConfigs.mutableInputLine);
        nbt.setTag("openGui", guiData);

        MainUDN.NETWORK.sendTo(new ClientPacketSyncDiarrheaNote(nbt), (EntityPlayerMP) player);
    }

    private void initNBT(ItemStack stack) {
        NBTTagCompound nbt = stack.getOrCreateSubCompound(MainUDN.MODID);
        NBTTagCompound data = nbt.getCompoundTag("immutableData");

        if (!data.hasKey("pagesCount")) {
            data.setInteger("pagesCount", (UDNConfigs.bookPagesCount / 2));
        }

        nbt.setTag("immutableData", data);
    }

    public void closeBook(ItemStack stack) {
        stack.getOrCreateSubCompound(MainUDN.MODID).setBoolean("open", false);
    }

    public void openBook(ItemStack stack) {
        stack.getOrCreateSubCompound(MainUDN.MODID).setBoolean("open", true);
    }

    @SideOnly(Side.CLIENT)
    public void openGui(EnumHand handIn, boolean mutableInputLine) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiDiarrheaNote(handIn, mutableInputLine));
    }

    @Override
    public boolean isNeedRegister() {
        return !UDNConfigs.immutableRemoveDiarrhea();
    }
}
