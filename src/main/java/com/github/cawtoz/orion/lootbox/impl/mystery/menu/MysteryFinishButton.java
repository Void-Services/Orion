package com.github.cawtoz.orion.lootbox.impl.mystery.menu;

import com.github.cawtoz.orion.Orion;
import com.github.cawtoz.orion.config.Config;
import com.github.cawtoz.orion.lootbox.impl.mystery.MysteryBox;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.Plugin;

public class MysteryFinishButton extends Button {
  private final MysteryBox box;
  
  public MysteryFinishButton(MysteryBox box) {
    this.box = box;
    setItem(Config.MYSTERY_ITEM_FILL);
    if (box.getUses() == 0) setItem(Config.MYSTERY_ITEM_CANCEL);
    if (box.getMaxUses() == box.getUses()) setItem(Config.MYSTERY_ITEM_FINISH);
  }
  
  public void click(Player player, ClickType clickType) {

    // cancel
    if (box.getUses() == 0) {
      PUtil.remove((HumanEntity)player);
      player.closeInventory();
      CSound.UI_BUTTON_CLICK.play((HumanEntity)player);
    }

    // finish
    if (box.getMaxUses() == box.getUses()) {
      box.removeItem((HumanEntity)player);
      box.finish((HumanEntity)player);
      Objects.requireNonNull(player);
      Bukkit.getScheduler().runTaskLater((Plugin)Orion.getInstance(), player::closeInventory, 1L);
    }

  }
}