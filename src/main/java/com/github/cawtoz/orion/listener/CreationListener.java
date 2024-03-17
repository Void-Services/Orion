package com.github.cawtoz.orion.listener;

import com.github.cawtoz.orion.creation.CState;
import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.util.cawtoz.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.ItemBuilder;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class CreationListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        HumanEntity target = event.getPlayer();
        if (PUtil.get(target) == null || PUtil.get(target).getCreation() == null) return;

        Creation creation = PUtil.get(event.getPlayer()).getCreation();
        String input = event.getMessage();

        switch (creation.getState()) {
            case NAME:
                creation.setName(input.split(" ")[0].toUpperCase());
                break;
            case DISPLAY_NAME:
                creation.setDisplayName(input);
                break;
            default:
                return;
        }

        event.setCancelled(true);
        creation.setState(CState.CREATING);

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if (Menu.isCancelCloseEvent()) return;
        HumanEntity target = event.getPlayer();
        if (PUtil.get(target) == null || PUtil.get(target).getCreation() == null) return;

        Creation creation = PUtil.get(target).getCreation();

        switch (creation.getState()) {
            case EDITING:
                PUtil.remove(target);
                return;
            case CREATING:
                creation.setState(CState.STOPPED);
                return;
            case LOOT:
                creation.getLoot().setContents(event.getInventory().getContents());
            case COLOR:
                creation.setState(CState.CREATING);
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        HumanEntity target = event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (PUtil.get(target) == null || PUtil.get(target).getCreation() == null || clickedInventory == null) return;

        Creation creation = PUtil.get(target).getCreation();
        Inventory topInventory = event.getView().getTopInventory();

        if (!(creation.isState(CState.CREATING) && clickedInventory.equals(topInventory) && event.getSlot() == 14)) return;

        ItemStack cursor = event.getCursor();
        if (cursor.getType() == Material.AIR) {
            CSound.ENTITY_VILLAGER_NO.play(target);
            return;
        }

        CSound.ENTITY_ITEM_PICKUP.play(target);

        ItemStack itemStack = new ItemBuilder(cursor)
                .setLore(creation.getItem().getItemMeta().getLore())
                .setAmount(1)
                .build();

        if (itemStack.getItemMeta() instanceof SkullMeta) {
            itemStack = new ItemBuilder(itemStack).setSkullOwner(((SkullMeta) cursor.getItemMeta()).getOwner()).build();
            System.out.println(((SkullMeta) itemStack.getItemMeta()).getOwner());
        }


        creation.setItem(itemStack);
        target.setItemOnCursor(null);

    }

}
