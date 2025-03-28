package com.artur114.ultradiarrheanote.common.temporallyeffects.client;

import com.artur114.ultradiarrheanote.common.temporallyeffects.ITemporallyEffect;
import com.artur114.ultradiarrheanote.common.temporallyeffects.TemporallyEffectsManagerBase;
import com.artur114.ultradiarrheanote.common.util.nbt.IWriteToNBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ClientTemporallyEffectsManager extends TemporallyEffectsManagerBase implements IClientTemporallyEffectsManager {
    public ClientTemporallyEffectsManager(EntityLivingBase entity) {
        super(entity);
    }

    private void addEffect(ITemporallyEffect effect) {
        this.effects.add(effect);
        effect.onStartTick(entity);
    }

    @Override
    public void processSyncData(NBTTagCompound data) {
        if (data.hasKey("newEffect")) {
            try {
                this.addEffect((ITemporallyEffect) IWriteToNBT.initObjFromString(data.getCompoundTag("newEffect")));
            } catch (Exception e) {
                new RuntimeException(e).printStackTrace(System.err);
            }
        }

        if (data.hasKey("allData")) {
            this.setData(data.getCompoundTag("allData"));
        }
    }

    private void setData(NBTTagCompound nbt) {
        NBTTagList list = nbt.getTagList("effects", 10);
        this.effects.clear();

        for (int i = 0; i != list.tagCount(); i++) {
            try {
                this.addEffect((ITemporallyEffect) IWriteToNBT.initObjFromString(list.getCompoundTagAt(i)));
            } catch (Exception e) {
                new RuntimeException(e).printStackTrace(System.err);
            }
        }
    }
}
