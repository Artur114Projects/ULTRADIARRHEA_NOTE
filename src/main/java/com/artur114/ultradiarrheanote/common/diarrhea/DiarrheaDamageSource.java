package com.artur114.ultradiarrheanote.common.diarrhea;

import net.minecraft.util.DamageSource;

public class DiarrheaDamageSource extends DamageSource {
    public static final DiarrheaDamageSource DIARRHEA_DAMAGE_SOURCE = (DiarrheaDamageSource) new DiarrheaDamageSource("diarrhea").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute();
    public DiarrheaDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }
}
