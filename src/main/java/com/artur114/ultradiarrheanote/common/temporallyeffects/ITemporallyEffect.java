package com.artur114.ultradiarrheanote.common.temporallyeffects;

import com.artur114.ultradiarrheanote.common.util.nbt.IReadFromNBT;
import com.artur114.ultradiarrheanote.common.util.nbt.IWriteToNBT;
import net.minecraft.entity.EntityLivingBase;

public interface ITemporallyEffect extends IReadFromNBT, IWriteToNBT {
    boolean update(EntityLivingBase entity);
    void onStartTick(EntityLivingBase entity);
}
