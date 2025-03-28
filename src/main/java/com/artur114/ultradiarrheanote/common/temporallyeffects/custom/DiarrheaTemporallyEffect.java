package com.artur114.ultradiarrheanote.common.temporallyeffects.custom;

import com.artur114.ultradiarrheanote.client.audio.DiarrheaSound;
import com.artur114.ultradiarrheanote.client.fx.particle.ParticleDiarrheaChunk;
import com.artur114.ultradiarrheanote.client.fx.particle.ParticleDiarrheaCircle;
import com.artur114.ultradiarrheanote.common.diarrhea.DiarrheaDamageSource;
import com.artur114.ultradiarrheanote.common.temporallyeffects.TemporallyEffectBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class DiarrheaTemporallyEffect extends TemporallyEffectBase {
    public static final int DIARRHEA_DURATION = 10 * 20;

    public DiarrheaTemporallyEffect() {
        super(DIARRHEA_DURATION);
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
        } else if (duration == DIARRHEA_DURATION) {
            entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, DIARRHEA_DURATION, 0));
            entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, DIARRHEA_DURATION, 0));
        }
    }

    protected void serverUpdate(EntityLivingBase entity) {
        if (DIARRHEA_DURATION - duration >= 10 && duration % 5 == 0) {
            entity.attackEntityFrom(DiarrheaDamageSource.DIARRHEA_DAMAGE_SOURCE, 20 * MathHelper.clamp((1.0F - (float) duration / DIARRHEA_DURATION) + 0.2F, 0, 1));
        }
    }

    @SideOnly(Side.CLIENT)
    protected void onClientStartTick(EntityLivingBase entity) {
        Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleDiarrheaCircle(entity.world, entity, duration, MathHelper.clamp(DIARRHEA_DURATION - duration, 0, 9)));
    }

    @SideOnly(Side.CLIENT)
    protected void clientUpdate(EntityLivingBase entity) {
        if (DIARRHEA_DURATION - duration >= 10 && entity.getHealth() > 0) {
            this.spawnDiarrheaChunks(entity);

            if (duration == DIARRHEA_DURATION - 10) Minecraft.getMinecraft().getSoundHandler().playSound(new DiarrheaSound(entity));
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
