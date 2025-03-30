package com.artur114.ultradiarrheanote.common.util.data;

import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = MainUDN.MODID)
@Mod.EventBusSubscriber(modid = MainUDN.MODID)
public class UDNConfigs {

    @Config.RequiresMcRestart
    @Config.LangKey(MainUDN.MODID + ".cfg.sub.rd")
    @Config.Comment({
            "if (true) removes everything related to diarrhea (except the mod name)",
            "a game restart is required for the changes to take effect"
    })
    public static boolean removeDiarrhea = false;
    private static boolean immutableRemoveDiarrhea;

    @Config.LangKey(MainUDN.MODID + ".cfg.sub.pc")
    @Config.Comment("number of pages in books, does not apply to existing books")
    public static int bookPagesCount = 20;

    @Config.LangKey(MainUDN.MODID + ".cfg.sub.mil")
    @Config.Comment({
            "if (true) you will be able to delete text in the input line",
            "you will be able to delete text in the input line, and if there is text in the input line, closing/changing the page will not confirm the line but will clear it"
    })
    public static boolean mutableInputLine = false;

    @Config.LangKey(MainUDN.MODID + ".cfg.sub.ckb")
    @Config.Comment("if (true) you can kill a boss by writing his localized name in the notebook")
    public static boolean canKillBosses = true;

    @Config.LangKey(MainUDN.MODID + ".cfg.sub.tpe")
    @Config.Comment("time after which the notebook effect is applied (in seconds)")
    public static int timeToPerformEffect = 60;

    @Config.LangKey(MainUDN.MODID + ".cfg.sub.med")
    @Config.Comment("the maximum distance a mob or boss can be from a book to apply the effect")
    public static int maxBookEffectDistance = 256;

    @Config.LangKey(MainUDN.MODID + ".cfg.sub.had")
    @Config.Comment("damage that will be dealt by the death note")
    public static int heartAttackDamage = Integer.MAX_VALUE;

    @Config.LangKey(MainUDN.MODID + ".cfg.sub.tde")
    @Config.Comment("time that diarrhea will act")
    public static int timeDiarrheaEffect = 10;

    @Config.LangKey(MainUDN.MODID + ".cfg.sub.mdd")
    @Config.Comment("the maximum damage that diarrhea will cause")
    public static int maxDiarrheaDamage = 20;


    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MainUDN.MODID)) {
            ConfigManager.sync(MainUDN.MODID, Config.Type.INSTANCE);
        }
    }

    public static void preInit(FMLPreInitializationEvent e) {
        immutableRemoveDiarrhea = removeDiarrhea;
    }

    public static boolean immutableRemoveDiarrhea() {
        return immutableRemoveDiarrhea;
    }
}
