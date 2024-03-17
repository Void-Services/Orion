package com.github.cawtoz.orion.config;

import org.bukkit.inventory.ItemStack;

import com.github.cawtoz.orion.util.cawtoz.file.FileConfig;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class Config {

    public static boolean LOOT_BOX_VIEW_ENABLE;
    public static String LOOT_BOX_VIEW_CLICK;
    public static boolean LOOT_BOX_USE_ENABLE;
    public static String LOOT_BOX_USE_CLICK;

    public static ItemStack MYSTERY_ITEM_FILL;
    public static ItemStack MYSTERY_ITEM_USE;
    public static ItemStack MYSTERY_ITEM_CANCEL;
    public static ItemStack MYSTERY_ITEM_FINISH;

    public Config() {
        FileConfig file = new FileConfig("config.yml");

        LOOT_BOX_VIEW_ENABLE = file.getBoolean("LOOT_BOX.VIEW.ENABLE");
        LOOT_BOX_VIEW_CLICK = file.getString("LOOT_BOX.VIEW.CLICK");
        LOOT_BOX_USE_ENABLE = file.getBoolean("LOOT_BOX.USE.ENABLE");
        LOOT_BOX_USE_CLICK = file.getString("LOOT_BOX.USE.CLICK");

        MYSTERY_ITEM_FILL =  file.getItem("MYSTERY_BOX.ITEMS.FILL");
        MYSTERY_ITEM_USE =  file.getItem("MYSTERY_BOX.ITEMS.USE");
        MYSTERY_ITEM_CANCEL =  file.getItem("MYSTERY_BOX.ITEMS.CANCEL");
        MYSTERY_ITEM_FINISH =  file.getItem("MYSTERY_BOX.ITEMS.FINISH");
    }

}
