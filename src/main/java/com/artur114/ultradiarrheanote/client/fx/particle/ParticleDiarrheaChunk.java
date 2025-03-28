package com.artur114.ultradiarrheanote.client.fx.particle;

import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleAtlasSprite;
import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleBase;
import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleSprite;
import com.artur114.ultradiarrheanote.client.init.InitParticleSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class ParticleDiarrheaChunk extends ParticleBase<ParticleSprite> {
    public ParticleDiarrheaChunk(World worldIn, EntityLivingBase entity, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, entity.posX, entity.posY + (entity.height / 2.4) + (worldIn.rand.nextFloat() / 10), entity.posZ, xSpeedIn, ySpeedIn, zSpeedIn);

        this.setSprite(InitParticleSprite.PARTICLE_DIARRHEA_CHUNK);
        this.bindSprite();

        this.particleGravity = 1.0F;
        this.particleScale /= 1.6F;
    }
}
