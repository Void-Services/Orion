package com.github.cawtoz.orion.util.cawtoz;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public enum CMaterial {

    PLAYER_HEAD(3, "SKULL_ITEM", "SKULL"),
    LIME_WOOL(5, "WOOL"),
    RED_WOOL(14, "WOOL"),
    BLACK_STAINED_GLASS_PANE(15, "STAINED_GLASS_PANE"),
    CHEST("LOCKED_CHEST"),
    FIREWORK_ROCKET("FIREWORK"),
    COMMAND_BLOCK("COMMAND");

    private int data;
    private String[] materials;
    private final String materialName;

    CMaterial(String... materialNames) {
        this.materials = materialNames;
        materialName = lookMaterialName();
    }

    CMaterial(int data, String... materialNames) {
        this.data = data;
        this.materials = materialNames;
        materialName = lookMaterialName();
    }

    public Material getMaterial() {
        return parseMaterial(materialName);
    }

    public ItemStack getItem() {
        if (materialName == null) return null;
        return createItem();
    }

    private ItemStack createItem() {
        ItemStack itemStack = new ItemStack(getMaterial());
        itemStack.setDurability((short) data);
        return itemStack;
    }

    private String lookMaterialName() {
        if (parseMaterial(name()) != null) return name();
        for (String materialName : materials) {
            if (parseMaterial(materialName) != null) return materialName;
        }
        return null;
    }

    private Material parseMaterial(String materialName) {
        try {
            return Material.valueOf(materialName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
