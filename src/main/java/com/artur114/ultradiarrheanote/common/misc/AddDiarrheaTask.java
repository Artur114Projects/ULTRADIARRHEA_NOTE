package com.artur114.ultradiarrheanote.common.misc;

import com.artur114.ultradiarrheanote.common.events.managers.TimerTasksManager;
import com.artur114.ultradiarrheanote.common.temporallyeffects.TemporallyEffectsHandler;
import com.artur114.ultradiarrheanote.common.temporallyeffects.custom.DiarrheaTemporallyEffect;
import com.artur114.ultradiarrheanote.common.util.data.UDNConfigs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddDiarrheaTask implements TimerTasksManager.ITaskCanSave {
    private final int maxDistance = UDNConfigs.maxBookEffectDistance;
    private final int maxTime = UDNConfigs.timeToPerformEffect * 20;
    protected List<UUID> uuidsToDiarrhea = new ArrayList<>();
    private int time;


    public AddDiarrheaTask(World world, BlockPos pos, String name) {
        this.uuidsToDiarrhea = this.compileUUIDsToDiarrhea(world, pos, name);
    }

    public AddDiarrheaTask() {}

    @Override
    public void tick() {
        if (!this.isFinish()) {
            this.time++;
        } else {
            return;
        }

        if (time >= maxTime) {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            for (UUID id : this.uuidsToDiarrhea) {
                Entity entity = server.getEntityFromUuid(id);
                if (entity instanceof EntityLivingBase) {
                    this.performEffect((EntityLivingBase) entity);
                }
            }
            this.uuidsToDiarrhea.clear();
        }
    }

    @Override
    public boolean isFinish() {
        return uuidsToDiarrhea.isEmpty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for (int i = 0; i != nbt.getInteger("uuidsCount"); i++) uuidsToDiarrhea.add(nbt.getUniqueId("uuid" + i));
        this.time = nbt.getInteger("time");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        for (int i = 0; i != uuidsToDiarrhea.size(); i++) nbt.setUniqueId("uuid" + i, uuidsToDiarrhea.get(i));
        nbt.setString("className", AddDiarrheaTask.class.getName());
        nbt.setInteger("uuidsCount", uuidsToDiarrhea.size());
        nbt.setInteger("time", time);
        return nbt;
    }

    private List<UUID> compileUUIDsToDiarrhea(World world, BlockPos pos, String name) {
        List<UUID> ret = new ArrayList<>();
        EntityPlayer player = world.getPlayerEntityByName(name);
        if (player != null) ret.add(player.getUniqueID());

        List<EntityLivingBase> livings = world.getEntities(EntityLivingBase.class, input -> {
            if (input == null) return false;
            return ((input.hasCustomName() && (input.isNonBoss() || UDNConfigs.canKillBosses)) || (!input.isNonBoss() && UDNConfigs.canKillBosses)) && (input.getName().equals(name) || (!input.isNonBoss() && input.getDisplayName().getFormattedText().equals(name))) && (Math.sqrt(input.getPosition().distanceSq(pos)) < maxDistance);
        });

        for (EntityLivingBase living : livings) {
            ret.add(living.getUniqueID());
        }

        return ret;
    }

    private void performEffect(EntityLivingBase entity) {
        if (!UDNConfigs.immutableRemoveDiarrhea()) {
            TemporallyEffectsHandler.addEffectToLiving(entity, new DiarrheaTemporallyEffect(UDNConfigs.timeDiarrheaEffect));
        } else {
            entity.attackEntityFrom(HeartAttackDamageSource.HEART_ATTACK_DAMAGE_SOURCE, UDNConfigs.heartAttackDamage);
        }
    }
}
