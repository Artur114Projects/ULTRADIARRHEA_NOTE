package com.artur114.ultradiarrheanote.common.init;

import com.artur114.ultradiarrheanote.common.item.*;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class InitItems {
    public static final List<BaseItem> ITEMS = new ArrayList<>();

    public static final Item DEATH_NOTE = new ItemDeathNote("death_note");
    public static final Item ULTRADIARRHEA_NOTE = new ItemUltraDiarrheaNote("ultra_diarrhea_note");
}
