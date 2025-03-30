package com.artur114.ultradiarrheanote.common.misc;


public class HeartAttackDamageSource extends DiarrheaDamageSource {
    public static final HeartAttackDamageSource HEART_ATTACK_DAMAGE_SOURCE = (HeartAttackDamageSource) new HeartAttackDamageSource("heartAttack").setDamageIsAbsolute().setDamageAllowedInCreativeMode().setMagicDamage().setDamageBypassesArmor().setExplosion();
    public HeartAttackDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }
}
