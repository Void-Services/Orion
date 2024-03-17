package com.github.cawtoz.orion.util.cawtoz.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
@Getter @Setter
public abstract class Button implements Listener {


    private ItemStack item;

    public Button() {
        item = new ItemStack(Material.BEDROCK);

    }

    public void setMaterial(Material material) {
        item.setType(material);
    }

    public void setDisplayName(String displayName) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        item.setItemMeta(itemMeta);
    }

    public void setLore(String... lore) {
        List<String> lines = new ArrayList<>();
        for (String line : lore) lines.add(ChatColor.translateAlternateColorCodes('&', line));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lines);
        item.setItemMeta(itemMeta);
    }

    public void addLore(String... lore) {

        List<String> newLines = new ArrayList<>();
        for (String line : lore) newLines.add(ChatColor.translateAlternateColorCodes('&', line));

        ItemMeta itemMeta = item.getItemMeta();
        List<String> oldLines = itemMeta.getLore();
        List<String> lines = new ArrayList<>();

        if (oldLines != null) lines.addAll(oldLines);
        lines.addAll(newLines);

        itemMeta.setLore(lines);
        item.setItemMeta(itemMeta);

    }

    public abstract void click(Player player, ClickType clickType);

}
