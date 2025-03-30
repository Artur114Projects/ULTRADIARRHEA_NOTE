package com.artur114.ultradiarrheanote.common.item;

import com.artur114.ultradiarrheanote.common.util.data.UDNConfigs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemDeathNote extends ItemUltraDiarrheaNote {
    public ItemDeathNote(String name) {
        super(name);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean isNeedRegister() {
        return UDNConfigs.immutableRemoveDiarrhea();
    }
}
