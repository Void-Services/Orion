package com.github.cawtoz.orion.util.cawtoz.file;

import com.github.cawtoz.orion.util.cawtoz.CC;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class SectionConfig implements ConfigInterface {
    private final ConfigurationSection section;
    private ConfigUtil configUtil;

    public SectionConfig(ConfigurationSection section) {
        this.section = section;
        configUtil = new ConfigUtil(section);
    }

    @Override
    public Material getMaterial(String path) { return configUtil.getMaterial(path); }

    @Override
    public ItemStack getItem(String path) { return configUtil.getItem(path); }

    @Override
    public void setItem(String path, ItemStack item) { configUtil.setItem(path, item); }

    @Override
    public ItemStack[] getFromBase64(String path) { return configUtil.getFromBase64(path); }

    @Override
    public void setToBase64(String path, ItemStack[] itemStacks) { configUtil.setToBase64(path, itemStacks); }

    @Override
    public List<Color> getColors(String path) { return configUtil.getColors(path); }

    public String getString(String string) { return section.getString(CC.format(string)); }

    public int getInt(String string) { return section.getInt(string); }

    public boolean getBoolean(String string) { return section.getBoolean(string); }

    public void set(String s, Object o) { section.set(s, o); }

}