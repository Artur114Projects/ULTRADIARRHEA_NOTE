package com.artur114.ultradiarrheanote.common.advancements.critereon;

import com.artur114.ultradiarrheanote.main.MainUDN;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Set;

public class DragonKilledOfDiarrhea implements ICriterionTrigger<DragonKilledOfDiarrhea.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(MainUDN.MODID, "dragon_killed_of_diarrhea");
    private final Map<PlayerAdvancements, DragonKilledOfDiarrhea.Listeners> listeners = Maps.newHashMap();

    public ResourceLocation getId() {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<DragonKilledOfDiarrhea.Instance> listener) {
        DragonKilledOfDiarrhea.Listeners ticktrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (ticktrigger$listeners == null)
        {
            ticktrigger$listeners = new DragonKilledOfDiarrhea.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, ticktrigger$listeners);
        }

        ticktrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<DragonKilledOfDiarrhea.Instance> listener) {
        DragonKilledOfDiarrhea.Listeners ticktrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (ticktrigger$listeners != null)
        {
            ticktrigger$listeners.remove(listener);

            if (ticktrigger$listeners.isEmpty())
            {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    public DragonKilledOfDiarrhea.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new DragonKilledOfDiarrhea.Instance();
    }

    public void trigger(EntityPlayerMP player) {
        DragonKilledOfDiarrhea.Listeners ticktrigger$listeners = this.listeners.get(player.getAdvancements());

        if (ticktrigger$listeners != null) {
            ticktrigger$listeners.trigger();
        }
    }

    public static class Instance extends AbstractCriterionInstance {
        public Instance() {
            super(DragonKilledOfDiarrhea.ID);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<DragonKilledOfDiarrhea.Instance>> listeners = Sets.<ICriterionTrigger.Listener<DragonKilledOfDiarrhea.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<DragonKilledOfDiarrhea.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<DragonKilledOfDiarrhea.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger() {
            for (ICriterionTrigger.Listener<DragonKilledOfDiarrhea.Instance> listener : Lists.newArrayList(this.listeners)) {
                listener.grantCriterion(this.playerAdvancements);
            }
        }
    }
}
