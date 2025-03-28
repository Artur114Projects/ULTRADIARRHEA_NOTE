package com.artur114.ultradiarrheanote.common.temporallyeffects;

import net.minecraft.entity.EntityLivingBase;

import java.util.*;

public abstract class TemporallyEffectsManagerBase implements ITemporallyEffectsManager {
    protected Set<ITemporallyEffect> effects = new HashSet<>();
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
    public EntityLivingBase getEntity() {
        return entity;
    }

    @Override
    public boolean isEmpty() {
        return effects.isEmpty();
    }
}
