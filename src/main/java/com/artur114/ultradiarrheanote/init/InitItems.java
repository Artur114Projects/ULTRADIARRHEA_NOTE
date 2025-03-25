package com.artur114.ultradiarrheanote.init;

import com.artur114.ultradiarrheanote.item.ItemUltraDiarrheaNote;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class InitItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item ULTRADIARRHEA_NOTE = new ItemUltraDiarrheaNote("ultra_diarrhea_note");
}
