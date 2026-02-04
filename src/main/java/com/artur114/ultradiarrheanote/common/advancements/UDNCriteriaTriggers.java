package com.artur114.ultradiarrheanote.common.advancements;

import com.artur114.ultradiarrheanote.common.advancements.critereon.DragonKilledOfDiarrhea;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class UDNCriteriaTriggers {
    public static final DragonKilledOfDiarrhea DRAGON_KILLED_OF_DIARRHEA = CriteriaTriggers.register(new DragonKilledOfDiarrhea());

    public static void preInit(FMLPreInitializationEvent e) {}//<- This is necessary so that the JVM loads this class and registration occurs. 
}
