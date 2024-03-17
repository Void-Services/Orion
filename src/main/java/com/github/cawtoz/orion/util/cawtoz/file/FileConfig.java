package com.github.cawtoz.orion.util.cawtoz.file;

import lombok.Getter;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.github.cawtoz.orion.Orion;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class FileConfig extends YamlConfiguration implements ConfigInterface {

    private final File file;
    @Getter
    private final ConfigUtil configUtil;

    public FileConfig(String fileName) {
        configUtil = new ConfigUtil(getConfigurationSection(""));
        Plugin plugin = Orion.getInstance();
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) plugin.saveResource(fileName, false);
        reloadConfig();
    }

    public void reloadConfig() {
        try {
            load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefault(String path, Object value) {
        if (!isSet(path)) {
            set(path, value);
        }
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

}
