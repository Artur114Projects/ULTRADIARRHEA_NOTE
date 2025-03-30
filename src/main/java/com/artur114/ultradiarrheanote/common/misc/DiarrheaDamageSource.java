package com.artur114.ultradiarrheanote.common.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;

public class DiarrheaDamageSource extends EntityDamageSource /* <- It is necessary that diarrhea could damage the ender dragon */ {
    public static final DiarrheaDamageSource DIARRHEA_DAMAGE_SOURCE = (DiarrheaDamageSource) new DiarrheaDamageSource("diarrhea").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute().setExplosion();
    public DiarrheaDamageSource(String damageTypeIn) {
        super(damageTypeIn, null);
    }

    @Override
    public boolean getIsThornsDamage() {
        return true;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
        EntityLivingBase entitylivingbase = entityLivingBaseIn.getAttackingEntity();
        String s = "death.attack." + this.damageType;
        String s1 = s + ".player";
        return entitylivingbase != null && I18n.canTranslate(s1) ? new TextComponentTranslation(s1, new Object[] {entityLivingBaseIn.getDisplayName(), entitylivingbase.getDisplayName()}) : new TextComponentTranslation(s, new Object[] {entityLivingBaseIn.getDisplayName()});
    }

    @Nullable
    @Override
    public Entity getTrueSource() {
        return null;
    }

    @Nullable
    @Override
    public Vec3d getDamageLocation() {
        return null;
    }

    @Override
    public boolean isDifficultyScaled() {
        return false;
    }
}
