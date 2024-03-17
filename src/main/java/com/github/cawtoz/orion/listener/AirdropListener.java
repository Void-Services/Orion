package com.github.cawtoz.orion.listener;

import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.cawtoz.orion.lootbox.impl.AirdropBox;
import com.github.cawtoz.orion.util.cawtoz.CSound;

public class AirdropListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getClickedBlock() == null || !(event.getClickedBlock().getState() instanceof Dispenser)) return;

        Player player = event.getPlayer();
        Dispenser dispenser = (Dispenser) event.getClickedBlock().getState();
        if (!(AirdropBox.airdropsBlocks.contains(dispenser) && player.isSneaking() && event.getAction() == Action.LEFT_CLICK_BLOCK)) return;

        Inventory playerInventory = player.getInventory();
        Inventory dispenserInventory = dispenser.getInventory();

        for (ItemStack item : dispenserInventory.getContents()) {
            if (item == null) continue;
            if (playerInventory.firstEmpty() == -1) player.getWorld().dropItem(player.getLocation(), item);
            else playerInventory.addItem(item);
        }

        dispenserInventory.setContents(new ItemStack[9]);
        CSound.ENTITY_GENERIC_EXPLODE.play(player);
        dispenser.getLocation().getBlock().setType(Material.AIR);
        AirdropBox.airdropsBlocks.remove(dispenser);

    }

}
