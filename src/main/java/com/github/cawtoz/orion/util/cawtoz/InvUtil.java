package com.github.cawtoz.orion.util.cawtoz;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class InvUtil {

    public static void addItem(HumanEntity player, ItemStack item) {
        if (isFull(player.getInventory())) player.getWorld().dropItem(player.getLocation(), item);
        else player.getInventory().addItem(item);
    }

    public static void addItems(HumanEntity player, List<ItemStack> itemStacks) {
        itemStacks.forEach(item -> addItem(player, item));
    }

    public static boolean isFull(Inventory inventory) {
        return inventory.firstEmpty() == -1;
    }

    public static boolean hasItems(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                return true;
            }
        }
        return false;
    }


}
