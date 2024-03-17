package com.github.cawtoz.orion.lootbox;

import com.github.cawtoz.orion.Orion;
import com.github.cawtoz.orion.util.cawtoz.CC;
import com.github.cawtoz.orion.util.cawtoz.file.SectionConfig;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.lootbox.impl.InstantBox;
import com.github.cawtoz.orion.lootbox.impl.mystery.MysteryBox;
import com.github.cawtoz.orion.util.cawtoz.file.FileConfig;
import com.github.cawtoz.orion.lootbox.impl.AirdropBox;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class LootBoxManager {

    private static final HashBasedTable<String, LootBoxType, LootBox> boxes = HashBasedTable.create();

    public static LootBox getBox(String name, LootBoxType type) {
        return boxes.get(name, type);
    }

    public static List<LootBox> getBoxes(LootBoxType type) {
        return boxes.cellSet().stream().filter(box -> box.getColumnKey() == type).map(Table.Cell::getValue).collect(Collectors.toList());
    }

    public static List<String> getBoxNames(LootBoxType type) {
        return getBoxes(type).stream().map(LootBox::getName).collect(Collectors.toList());
    }

    public void loadBoxes() {
        boxes.clear();
        Stream.of(LootBoxType.values()).forEach(type -> new FileConfig(type.getFileName()).getKeys(false).forEach(name -> loadBox(name, type)));
        Orion.getInstance().loadCommands();
    }

    private void loadBox(String name, LootBoxType type) {

        LootBox box = buildBox(name, type); if (box == null) return;
        SectionConfig s = new SectionConfig(new FileConfig(type.getFileName()).getConfigurationSection(name));

        box.setDisplayName(s.getString("DISPLAY_NAME"));
        box.setItem(s.getItem("ITEM"));
        box.setLoot(s.getFromBase64("LOOT"));
        box.setColors(s.getColors("COLORS"));
        box.setNBT();

        switch (type) {
            case INSTANT:
                ((InstantBox) box).setAmountReward(s.getInt("AMOUNT_REWARD"));
                break;
            case MYSTERY:
                ((MysteryBox) box).setMaxUses(s.getInt("MAX_USES"));
        }

        ItemStack item = box.getItem().clone();
        ItemMeta itemMeta = item.getItemMeta();
        List<String> newLore = new ArrayList<>();

        for (String line : itemMeta.getLore()) {
            if (line.contains("%LOOT%")) {
                box.getLoot().forEach(itemStack -> {
                    if (itemStack != null) {
                        String displayName = itemStack.getItemMeta().getDisplayName();
                        newLore.add("&7 x" + itemStack.getAmount() + " &f" + ((displayName != null) ? displayName : itemStack.getType().name()));
                    }
                });
            }
            else newLore.add(line);
        }

        itemMeta.setLore(CC.format(newLore));
        item.setItemMeta(itemMeta);
        box.setGiveItem(item);

        boxes.put(name, type, box);

    }

    private LootBox buildBox(String name, LootBoxType type) {
        switch (type) {
            case INSTANT: return new InstantBox(name);
            case MYSTERY: return new MysteryBox(name);
            case AIRDROP: return new AirdropBox(name);
            default: return null;
        }
    }

}
