package com.artur114.ultradiarrheanote.common.item;

import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemIconManager extends BaseItem {
    public ItemIconManager(String name) {
        super(name);
        this.addPropertyOverride(new ResourceLocation("iconId"), new IItemPropertyGetter() {
            private final int maxIcons = 2;
            private int currentIcon = 1;
            private long lastTime = 0;

            @Override
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                int iconId = stack.getOrCreateSubCompound(MainUDN.MODID).getInteger("iconId");
                if (iconId != 0) {
                    return iconId;
                } else {
                    if (System.currentTimeMillis() - lastTime >= 1000) {
                        if (currentIcon + 1 > maxIcons) {
                            currentIcon = 1;
                        } else {
                            currentIcon++;
                        }

                        this.lastTime = System.currentTimeMillis();
                    }

                    return currentIcon;
                }
            }
        });
    }
}
