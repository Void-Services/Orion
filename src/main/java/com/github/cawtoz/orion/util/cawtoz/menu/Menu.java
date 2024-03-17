package com.github.cawtoz.orion.util.cawtoz.menu;

import com.github.cawtoz.orion.Orion;
import com.github.cawtoz.orion.util.cawtoz.CC;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public abstract class Menu {

    @Setter
    private static boolean cancelCloseEvent;
    public static final HashMap<Inventory, Menu> menus = new HashMap<>();

    private final HashMap<Integer, Button> buttons = new HashMap<>();
    private Inventory inventory = Bukkit.createInventory(null, 9, "");
    private String title;

    protected abstract void create(Player player);

    public void open(Player player) {
        clear();
        create(player);
        Bukkit.getScheduler().runTaskLater(Orion.getInstance(), () -> {
            cancelCloseEvent = true;
            player.openInventory(inventory);
            cancelCloseEvent = false;
            Menu.menus.put(inventory, this);
        },1);
    }

    public static Boolean isCancelCloseEvent() {
        return cancelCloseEvent;
    }

    protected String getTitle() {
        return title;
    }

    protected int getSize() {
        return inventory.getSize();
    }

    protected Inventory getInventory() {
        return inventory;
    }

    protected HashMap<Integer, Button> getButtons() {
        return buttons;
    }

    public Button getButton(int slot) {
        return buttons.get(slot);
    }

    public void setTitle(String title) {
        this.title = CC.format(title);
        ItemStack[] contents = inventory.getContents();
        inventory = Bukkit.createInventory(null, inventory.getSize(), title);
        inventory.setContents(contents);
    }

    public void setSize(int size) {
        ItemStack[] contents = inventory.getContents();
        CC.sendConsole("&cHola 1");
        inventory = Bukkit.createInventory(null, size, title);
        CC.sendConsole("&cHola 2");
        inventory.setContents(contents);
    }

    public void setButton(int slot, Button button) {
        buttons.put(slot, button);
        inventory.setItem(slot, button.getItem());
    }

    public void setFill(ItemStack item) {
        for (int i = 0; i < getSize(); i++) {
            inventory.setItem(i, item);
        }
    }

    public void clear() {
        inventory.clear();
        buttons.clear();
    }

}
