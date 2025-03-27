package com.artur114.ultradiarrheanote.client.fx.particle;

import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleAtlasSprite;
import com.artur114.ultradiarrheanote.client.fx.particle.util.ParticleBase;
import com.artur114.ultradiarrheanote.client.init.InitParticleSprite;
import com.artur114.ultradiarrheanote.client.util.RenderHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleDiarrheaCircle extends ParticleBase<ParticleAtlasSprite> {
    private final EntityLivingBase livingBase;
    private int currentTextureId = 0;

    public ParticleDiarrheaCircle(World worldIn, EntityLivingBase livingBase, int livingTime) {
        super(worldIn, livingBase.posX, livingBase.posY, livingBase.posZ);
        this.livingBase = livingBase;

        this.setSprite(InitParticleSprite.PARTICLE_DIARRHEA_CIRCLE);
        this.particleMaxAge = livingTime;
        this.particleScale = 1.0F;
        this.canCollide = false;
    }

    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        double x = RenderHandler.interpolate(prevPosX, posX, partialTicks) - interpPosX;
        double y = RenderHandler.interpolate(prevPosY, posY, partialTicks) - interpPosY;
        double z = RenderHandler.interpolate(prevPosZ, posZ, partialTicks) - interpPosZ;

        TextureAtlasSprite atlasSprite = this.sprite().atlasSprite(currentTextureId);

        double startU = atlasSprite.getMinU();
        double endU = atlasSprite.getMaxU();
        double startV = atlasSprite.getMinV();
        double endV = atlasSprite.getMaxV();

        float scale = this.particleScale;

        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;

        buffer.pos(x + -1 * scale, y + 0 * scale, z + 1  * scale).tex(endU, endV).color(this.particleRed, this.particleGreen, this.particleBlue, 1).lightmap(j, k).endVertex();
        buffer.pos(x + 1  * scale, y + 0 * scale, z + 1  * scale).tex(endU, startV).color(this.particleRed, this.particleGreen, this.particleBlue, 1).lightmap(j, k).endVertex();
        buffer.pos(x + 1  * scale, y + 0 * scale, z + -1 * scale).tex(startU, startV).color(this.particleRed, this.particleGreen, this.particleBlue, 1).lightmap(j, k).endVertex();
        buffer.pos(x + -1 * scale, y + 0 * scale, z + -1 * scale).tex(startU, endV).color(this.particleRed, this.particleGreen, this.particleBlue, 1).lightmap(j, k).endVertex();
    }


    @Override
    public void onUpdate() {
        super.onUpdate();

        if (livingBase.isDead) this.setExpired();

        this.posX = livingBase.posX;
        this.posY = livingBase.posY + 0.1;
        this.posZ = livingBase.posZ;

        this.prevPosX = livingBase.prevPosX;
        this.prevPosY = livingBase.prevPosY = 0.1;
        this.prevPosZ = livingBase.prevPosZ;

        if (this.currentTextureId < 9) {
            this.currentTextureId++;
        }
    }
}
