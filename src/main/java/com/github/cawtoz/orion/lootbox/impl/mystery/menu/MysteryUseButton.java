package com.github.cawtoz.orion.lootbox.impl.mystery.menu;

import com.github.cawtoz.orion.config.Config;
import com.github.cawtoz.orion.lootbox.impl.mystery.MysteryBox;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MysteryUseButton extends Button {
  private final int slot;
  
  private final MysteryBox box;
  
  public MysteryUseButton(int slot, MysteryBox box) {
    this.box = box;
    this.slot = slot;
    if (box.getUsedSlots().containsKey(Integer.valueOf(slot))) {
      setItem((ItemStack)box.getUsedSlots().get(Integer.valueOf(slot)));
    } else {
      setItem(Config.MYSTERY_ITEM_USE);
    } 
  }
  
  public void click(Player player, ClickType clickType) {
    if (this.box.getUses() != this.box.getMaxUses() && !this.box.getUsedSlots().containsKey(Integer.valueOf(this.slot))) {
      ItemStack item = this.box.getRandomItem();
      this.box.getReward().add(item);
      this.box.setUses(this.box.getUses() + 1);
      this.box.getUsedSlots().put(Integer.valueOf(this.slot), item);
      CSound.BLOCK_NOTE_BLOCK_PLING.play((HumanEntity)player);
      (new MysteryMenu(this.box)).open(player);
    } else {
      CSound.ENTITY_VILLAGER_NO.play((HumanEntity)player);
    } 
  }
}
