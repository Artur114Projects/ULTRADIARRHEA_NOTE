package com.artur114.ultradiarrheanote.common.diarrhea;

import com.artur114.ultradiarrheanote.common.events.managers.TimerTasksManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
    protected List<UUID> uuidsToDiarrhea = new ArrayList<>();
    private final int maxTime = 4 * 20;
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
                    entity.onKillCommand();
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

        List<EntityLiving> livings = world.getEntities(EntityLiving.class, input -> {
            if (input == null) return false;
            return input.hasCustomName() && input.getName().equals(name) && (Math.sqrt(input.getPosition().distanceSq(pos)) < 128);
        });

        for (EntityLiving living : livings) {
            ret.add(living.getUniqueID());
        }

        return ret;
    }
}
