package com.github.cawtoz.orion.util.cawtoz.file;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public interface ConfigInterface {

    Material getMaterial(String path);
    ItemStack getItem(String path);
    void setItem(String path, ItemStack item);

    ItemStack[] getFromBase64(String path);
    void setToBase64(String path, ItemStack[] itemStacks);
    List<Color> getColors(String path);

}
