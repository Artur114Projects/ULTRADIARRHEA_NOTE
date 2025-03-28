package com.artur114.ultradiarrheanote.client.audio;

import com.artur114.ultradiarrheanote.common.init.InitSounds;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class DiarrheaSound extends MovingSound {
    private final EntityLivingBase entity;
    public DiarrheaSound(EntityLivingBase entity) {
        super(InitSounds.DIARRHEA, SoundCategory.AMBIENT);

        this.repeatDelay = 0;
        this.entity = entity;
        this.repeat = true;
    }

    @Override
    public void update() {
        this.xPosF = (float) entity.posX;
        this.yPosF = (float) entity.posY;
        this.zPosF = (float) entity.posZ;

        if (entity.getHealth() <= 0) {
            this.donePlaying = true;
        }
    }
}
