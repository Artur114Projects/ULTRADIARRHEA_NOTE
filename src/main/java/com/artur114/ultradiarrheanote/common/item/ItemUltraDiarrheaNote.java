package com.artur114.ultradiarrheanote.common.item;

import com.artur114.ultradiarrheanote.client.gui.GuiDiarrheaNote;
import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ItemUltraDiarrheaNote extends BaseItem {
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
        this.openBook(playerIn.getHeldItem(handIn));
        if (worldIn.isRemote) {
            this.openGui(handIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    public  void closeBook(ItemStack stack) {
        stack.getOrCreateSubCompound(MainUDN.MODID).setBoolean("open", false);
    }

    public void openBook(ItemStack stack) {
        stack.getOrCreateSubCompound(MainUDN.MODID).setBoolean("open", true);
    }

    @SideOnly(Side.CLIENT)
    private void openGui(EnumHand handIn) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiDiarrheaNote(handIn));
    }
}
