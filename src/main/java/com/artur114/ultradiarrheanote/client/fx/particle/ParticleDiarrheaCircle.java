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
import net.minecraft.world.World;

public class ParticleDiarrheaCircle extends ParticleBase<ParticleAtlasSprite> {
    private final EntityLivingBase livingBase;
    private int currentTextureId = 0;

    public ParticleDiarrheaCircle(World worldIn, EntityLivingBase livingBase, int livingTime, int startTextureId) {
        super(worldIn, livingBase.posX, livingBase.posY, livingBase.posZ);
        this.livingBase = livingBase;

        this.setSprite(InitParticleSprite.PARTICLE_DIARRHEA_CIRCLE);
        this.currentTextureId = startTextureId;
        this.particleMaxAge = livingTime;
        this.particleScale = livingBase.height;
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


        double sin0 = MathHelper.sin(this.getInterpolatedAngle(partialTicks, ((Math.PI * 2) / 4) * 1));
        double cos0 = MathHelper.cos(this.getInterpolatedAngle(partialTicks, ((Math.PI * 2) / 4) * 1));
        buffer.pos(x + scale * cos0, y, z + scale * sin0).tex(endU, endV).color(this.particleRed, this.particleGreen, this.particleBlue, 1).lightmap(j, k).endVertex();
        double sin1 = MathHelper.sin(this.getInterpolatedAngle(partialTicks, ((Math.PI * 2) / 4) * 2));
        double cos1 = MathHelper.cos(this.getInterpolatedAngle(partialTicks, ((Math.PI * 2) / 4) * 2));
        buffer.pos(x + scale * cos1, y, z + scale * sin1).tex(endU, startV).color(this.particleRed, this.particleGreen, this.particleBlue, 1).lightmap(j, k).endVertex();
        double sin2 = MathHelper.sin(this.getInterpolatedAngle(partialTicks, ((Math.PI * 2) / 4) * 3));
        double cos2 = MathHelper.cos(this.getInterpolatedAngle(partialTicks, ((Math.PI * 2) / 4) * 3));
        buffer.pos(x + scale * cos2, y, z + scale * sin2).tex(startU, startV).color(this.particleRed, this.particleGreen, this.particleBlue, 1).lightmap(j, k).endVertex();
        double sin3 = MathHelper.sin(this.getInterpolatedAngle(partialTicks, ((Math.PI * 2) / 4) * 0));
        double cos3 = MathHelper.cos(this.getInterpolatedAngle(partialTicks, ((Math.PI * 2) / 4) * 0));
        buffer.pos(x + scale * cos3, y, z + scale * sin3).tex(startU, endV).color(this.particleRed, this.particleGreen, this.particleBlue, 1).lightmap(j, k).endVertex();
    }

    private float getInterpolatedAngle(float partialTicks, double offset) {
        return (float) RenderHandler.interpolate(this.prevParticleAngle + offset, this.particleAngle + offset, partialTicks);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (livingBase.getHealth() <= 0) this.setExpired();

        this.posX = livingBase.posX;
        this.posY = livingBase.posY + 0.1;
        this.posZ = livingBase.posZ;

        this.prevPosX = livingBase.prevPosX;
        this.prevPosY = livingBase.prevPosY + 0.1;
        this.prevPosZ = livingBase.prevPosZ;

        if (this.currentTextureId < 9) {
            this.currentTextureId++;
        }

        this.prevParticleAngle = this.particleAngle;
        this.particleAngle += 0.06F;
    }
}
