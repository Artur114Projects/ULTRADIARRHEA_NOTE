package com.artur114.ultradiarrheanote.client.audio;

import com.artur114.ultradiarrheanote.common.init.InitSounds;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class DiarrheaSound extends MovingSound {
    private final EntityLivingBase entity;
    private int duration;
    public DiarrheaSound(EntityLivingBase entity, int duration) {
        super(InitSounds.DIARRHEA, SoundCategory.NEUTRAL);

        this.duration = duration;
        this.repeatDelay = 0;
        this.entity = entity;
        this.repeat = true;
    }

    @Override
    public void update() {
        this.xPosF = (float) entity.posX;
        this.yPosF = (float) entity.posY;
        this.zPosF = (float) entity.posZ;

        this.duration--;

        if (entity.getHealth() <= 0 || entity.isDead || !entity.isAddedToWorld() || !(duration > 0)) {
            this.donePlaying = true;
        }
    }
}
