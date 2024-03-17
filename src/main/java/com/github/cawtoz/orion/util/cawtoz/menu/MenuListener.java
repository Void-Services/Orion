package com.github.cawtoz.orion.util.cawtoz.menu;

import com.github.cawtoz.orion.Orion;
import com.github.cawtoz.orion.util.cawtoz.menu.paginated.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class MenuListener implements Listener {

    private static boolean cancelClickEvent;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (cancelClickEvent) return;
        Inventory inventory = event.getInventory();
        Inventory clickedInventory = event.getClickedInventory();

        if (Menu.menus.get(inventory) == null || clickedInventory == null) return;
        if (!clickedInventory.equals(event.getView().getTopInventory())) return;

        event.setCancelled(true);

        int slot = event.getSlot();
        Menu menu = Menu.menus.get(inventory);
        ClickType clickType = event.getClick();
        Player player = (Player) event.getWhoClicked();

        if (menu.getButton(slot) != null) {
            cancelClickEvent = true;
            menu.getButton(slot).click(player, clickType);
            Bukkit.getScheduler().runTaskLater(Orion.getInstance(), () -> { cancelClickEvent = false; }, 1);
            return;
        }

        if (menu instanceof PaginatedMenu) {
            PaginatedMenu paginatedMenu = (PaginatedMenu) menu;
            if (paginatedMenu.getPageButton(slot) == null) return;
            paginatedMenu.getPageButton(slot).click(player, clickType);
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (Menu.menus.get(inventory) != null) {
            Menu.menus.remove(inventory);
        }
    }

}
