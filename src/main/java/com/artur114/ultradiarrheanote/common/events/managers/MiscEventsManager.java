package com.artur114.ultradiarrheanote.common.events.managers;

import com.artur114.ultradiarrheanote.common.advancements.UDNCriteriaTriggers;
import com.artur114.ultradiarrheanote.common.misc.DiarrheaDamageSource;
import com.artur114.ultradiarrheanote.common.util.data.UDNConfigs;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MiscEventsManager {
    public void livingDeathEvent(LivingHurtEvent e) {
        if (!e.getEntity().world.isRemote && e.getEntity() instanceof EntityDragon) {
            if (e.getSource() == DiarrheaDamageSource.DIARRHEA_DAMAGE_SOURCE && ((EntityDragon) e.getEntity()).getHealth() - e.getAmount() <= 0) {
                for (EntityPlayer player : e.getEntity().world.playerEntities) {
                    UDNCriteriaTriggers.DRAGON_KILLED_OF_DIARRHEA.trigger((EntityPlayerMP) player);
                }
            }
        }
    }
}
