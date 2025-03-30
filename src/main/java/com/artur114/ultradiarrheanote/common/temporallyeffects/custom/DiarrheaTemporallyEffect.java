package com.artur114.ultradiarrheanote.common.temporallyeffects.custom;

import com.artur114.ultradiarrheanote.client.audio.DiarrheaSound;
import com.artur114.ultradiarrheanote.client.fx.particle.ParticleDiarrheaChunk;
import com.artur114.ultradiarrheanote.client.fx.particle.ParticleDiarrheaCircle;
import com.artur114.ultradiarrheanote.common.misc.DiarrheaDamageSource;
import com.artur114.ultradiarrheanote.common.temporallyeffects.TemporallyEffectBase;
import com.artur114.ultradiarrheanote.common.util.data.UDNConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class DiarrheaTemporallyEffect extends TemporallyEffectBase {
    protected int maxDuration;

    public DiarrheaTemporallyEffect() {}

    public DiarrheaTemporallyEffect(int duration) {
        super(duration * 20);
        this.maxDuration = duration * 20;
    }

    @Override
    public boolean update(EntityLivingBase entity) {
        if (entity.world.isRemote) {
            this.clientUpdate(entity);
        } else {
            this.serverUpdate(entity);
        }

        return super.update(entity);
    }

    @Override
    public void onStartTick(EntityLivingBase entity) {
        if (entity.world.isRemote) {
            this.onClientStartTick(entity);
        } else if (duration == maxDuration) {
            entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, maxDuration, 0));
            entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, maxDuration, 0));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.maxDuration = nbt.getInteger("maxDuration");
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("maxDuration", maxDuration);
        return super.writeToNBT(nbt);
    }

    protected void serverUpdate(EntityLivingBase entity) {
        if (maxDuration - duration >= 10) {
            if (duration % 10 == 0) {
                entity.attackEntityFrom(DiarrheaDamageSource.DIARRHEA_DAMAGE_SOURCE, UDNConfigs.maxDiarrheaDamage * MathHelper.clamp((1.0F - (float) duration / maxDuration) + 0.2F, 0, 1));
            }
            entity.hurtResistantTime = 0;
        }
    }

    @SideOnly(Side.CLIENT)
    protected void onClientStartTick(EntityLivingBase entity) {
        Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleDiarrheaCircle(entity.world, entity, duration, MathHelper.clamp(maxDuration - duration, 0, 9)));
    }

    @SideOnly(Side.CLIENT)
    protected void clientUpdate(EntityLivingBase entity) {
        if (maxDuration - duration >= 10 && entity.getHealth() > 0) {
            this.spawnDiarrheaChunks(entity);

            if (duration == maxDuration - 10) Minecraft.getMinecraft().getSoundHandler().playSound(new DiarrheaSound(entity, duration));
        }
    }

    @SideOnly(Side.CLIENT)
    protected void spawnDiarrheaChunks(EntityLivingBase entity) {
        for (int i = 0; i != 16; i++) {
            Random rand = Minecraft.getMinecraft().world.rand;
            float horizontalSpeed = 1.8F;
            float verticalSpeed = 0.2F;

            double moveX = ((rand.nextDouble() - 0.5) * 2) * horizontalSpeed + entity.motionX;
            double moveY = ((rand.nextDouble() - 0.5) * 2) * verticalSpeed + entity.motionY;
            double moveZ = ((rand.nextDouble() - 0.5) * 2) * horizontalSpeed + entity.motionZ;

            Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleDiarrheaChunk(entity.world, entity, moveX, moveY, moveZ));
        }
    }
}
