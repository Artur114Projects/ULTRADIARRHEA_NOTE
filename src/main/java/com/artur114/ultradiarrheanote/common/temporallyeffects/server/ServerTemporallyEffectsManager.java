package com.artur114.ultradiarrheanote.common.temporallyeffects.server;

import com.artur114.ultradiarrheanote.common.network.ClientPacketSyncTemporallyEffects;
import com.artur114.ultradiarrheanote.common.temporallyeffects.ITemporallyEffect;
import com.artur114.ultradiarrheanote.common.temporallyeffects.TemporallyEffectsManagerBase;
import com.artur114.ultradiarrheanote.common.util.nbt.IWriteToNBT;
import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ServerTemporallyEffectsManager extends TemporallyEffectsManagerBase implements IServerTemporallyEffectsManager {
    public ServerTemporallyEffectsManager(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public void syncToClient(EntityPlayerMP clientIn) {
        NBTTagCompound nbt = this.createBaseSyncNBT();
        nbt.setTag("allData", this.serializeNBT());
        MainUDN.NETWORK.sendTo(new ClientPacketSyncTemporallyEffects(nbt), clientIn);
    }

    @Override
    public void addEffect(ITemporallyEffect effect) {
        this.effects.add(effect);
        this.onNewEffect(effect);
        effect.onStartTick(entity);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (ITemporallyEffect effect : effects) {
            list.appendTag(effect.writeToNBT(new NBTTagCompound()));
        }
        nbt.setTag("effects", list);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList list = nbt.getTagList("effects", 10);

        for (int i = 0; i != list.tagCount(); i++) {
            try {
                this.addEffect((ITemporallyEffect) IWriteToNBT.initObjFromString(list.getCompoundTagAt(i)));
            } catch (Exception e) {
                new RuntimeException(e).printStackTrace(System.err);
            }
        }
    }

    protected NBTTagCompound createBaseSyncNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("entityId", entity.getEntityId());
        nbt.setInteger("dimension", entity.dimension);
        return nbt;
    }

    protected void onNewEffect(ITemporallyEffect effect) {
        NBTTagCompound nbt = this.createBaseSyncNBT();
        nbt.setTag("newEffect", effect.writeToNBT(new NBTTagCompound()));
        MainUDN.NETWORK.sendToAllTracking(new ClientPacketSyncTemporallyEffects(nbt), entity);
        if (entity instanceof EntityPlayerMP) MainUDN.NETWORK.sendTo(new ClientPacketSyncTemporallyEffects(nbt), (EntityPlayerMP) entity);
    }
}
