package com.artur114.ultradiarrheanote.item;

import com.artur114.ultradiarrheanote.init.InitItems;
import com.artur114.ultradiarrheanote.main.MainUDN;
import com.artur114.ultradiarrheanote.register.IHasModel;
import net.minecraft.item.Item;

public abstract class BaseItem extends Item implements IHasModel {
    protected BaseItem(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);

        InitItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        MainUDN.PROXY.registerItemRenderer(this, 0, "inventory");
    }
}
