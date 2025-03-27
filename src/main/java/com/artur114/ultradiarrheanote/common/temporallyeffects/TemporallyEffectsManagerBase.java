package com.artur114.ultradiarrheanote.common.temporallyeffects;

import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class TemporallyEffectsManagerBase implements ITemporallyEffectsManager {
    protected List<ITemporallyEffect> effects = new ArrayList<>();
    protected final EntityLivingBase entity;

    public TemporallyEffectsManagerBase(EntityLivingBase entity) {
        this.entity = entity;
    }

    @Override
    public void update() {
        if (this.isEmpty()) {
            return;
        }

        effects.removeIf(effect -> !effect.update(entity));
    }

    @Override
    public boolean isEmpty() {
        return effects.isEmpty();
    }
}
