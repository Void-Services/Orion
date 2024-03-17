package com.github.cawtoz.orion.util.cawtoz.file;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.github.cawtoz.orion.util.ItemBuilder;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class ConfigUtil implements ConfigInterface {

    private final ConfigurationSection section;

    public ConfigUtil(ConfigurationSection section) {
        this.section = section;
    }

    @Override
    public Material getMaterial(String path) {
        try {
            return Material.valueOf(section.getString(path));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public ItemStack getItem(String path) {
        ItemBuilder item = new ItemBuilder(getMaterial(path + ".MATERIAL"))
                .setDurability(section.getInt(path + ".DATA"))
                .setName(section.getString(path + ".DISPLAY_NAME"))
                .setLore(section.getStringList(path + ".LORE"));
        if (item.build().getItemMeta() instanceof SkullMeta) {
            item.setSkullOwner(section.getString(path + ".SKULL_OWNER"));
        }
        return item.build();
    }

    @Override
    public void setItem(String path, ItemStack item) {
        section.set(path + ".DATA", item.getDurability());
        section.set(path + ".MATERIAL", item.getType().toString());
        if (item.getItemMeta() instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            section.set(path + ".SKULL_OWNER", skullMeta.getOwner());
        }
        section.set(path + ".DISPLAY_NAME", item.getItemMeta().getDisplayName());
        if (item.getItemMeta().getDisplayName() == null) section.set(path + ".DISPLAY_NAME", " ");
        section.set(path + ".LORE", item.getItemMeta().getLore());
    }

    @Override
    public ItemStack[] getFromBase64(String path) {
        return ItemBuilder.fromBase64(section.getString(path));
    }

    @Override
    public void setToBase64(String path, ItemStack[] itemStacks) {
        section.set(path, ItemBuilder.toBase64(itemStacks));
    }

    @Override
    public List<Color> getColors(String path) {
        List<Color> colors = new ArrayList<>();
        if (section.isConfigurationSection(path)) {
            ConfigurationSection colorsSection = section.getConfigurationSection(path);
            for (String key : colorsSection.getKeys(false)) {
                int red = colorsSection.getInt(key + ".RED");
                int green = colorsSection.getInt(key + ".GREEN");
                int blue = colorsSection.getInt(key + ".BLUE");
                colors.add(Color.fromRGB(red, green, blue));
            }
        }
        return colors;
    }

}
