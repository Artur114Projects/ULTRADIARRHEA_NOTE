package com.artur114.ultradiarrheanote.common.init;

import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber
public class InitSounds {
    private static final List<SoundEvent> SOUND_EVENTS = new ArrayList<>();

    public static SoundEvent DIARRHEA = create("diarrhea");

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> e) {
        for (SoundEvent soundEvent : SOUND_EVENTS) {
            if (soundEvent != null) {
                ForgeRegistries.SOUND_EVENTS.register(soundEvent);
            }
        }
        SOUND_EVENTS.clear();
    }

    private static SoundEvent create(String name) {
        ResourceLocation rl = new ResourceLocation(MainUDN.MODID, name);
        SoundEvent s = (new SoundEvent(rl)).setRegistryName(rl);
        SOUND_EVENTS.add(s);
        return s;
    }
}