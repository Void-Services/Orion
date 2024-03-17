package com.github.cawtoz.orion.listener;

import com.github.cawtoz.orion.lootbox.LootBoxManager;
import com.github.cawtoz.orion.lootbox.impl.mystery.menu.MysteryMenu;
import com.github.cawtoz.orion.util.cawtoz.menu.Menu;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.cawtoz.orion.lootbox.impl.mystery.MysteryBox;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.profile.*;

public class LootBoxListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getItem() == null) return;
        NBTItem nbtItem = new NBTItem(event.getItem());

        if (nbtItem.hasKey("name") && nbtItem.hasKey("type")) {
            event.setCancelled(true);

            String name = nbtItem.getString("name");
            LootBoxType type = LootBoxType.valueOf(nbtItem.getString("type"));

            HumanEntity target = event.getPlayer();
            Profile profile = PUtil.getOrCreate(target);


            profile.setLootBox(LootBoxManager.getBox(name, type).clone());
            profile.getLootBox().open(target, event.getAction());
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (PUtil.get(event.getWhoClicked()) == null) return;
        Profile profile = PUtil.get(event.getWhoClicked());
        if (profile.isLooking()) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (Menu.isCancelCloseEvent() || PUtil.get(event.getPlayer()) == null) return;
        Profile profile = PUtil.get(event.getPlayer());
        if (profile.isLooking()) profile.setLooking(false);
        if (profile.isUsing()) {
            System.out.println("test");
            new MysteryMenu((MysteryBox) profile.getLootBox()).open((Player) event.getPlayer());
        }
    }

}
