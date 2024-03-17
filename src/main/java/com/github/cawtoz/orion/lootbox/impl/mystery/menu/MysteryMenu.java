package com.github.cawtoz.orion.lootbox.impl.mystery.menu;

import com.github.cawtoz.orion.config.Config;
import com.github.cawtoz.orion.lootbox.impl.mystery.MysteryBox;
import com.github.cawtoz.orion.util.cawtoz.menu.Menu;
import java.util.Arrays;
import org.bukkit.entity.Player;

public class MysteryMenu extends Menu {
  private MysteryBox box;
  
  public MysteryMenu(MysteryBox box) {
    this.box = box;
  }
  
  protected void create(Player player) {
    setSize(54);
    setTitle(this.box.getDisplayName());
    setFill(Config.MYSTERY_ITEM_FILL);
    setButton(49, new MysteryFinishButton(this.box));
    Arrays.<Integer>asList(new Integer[] { Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(21), Integer.valueOf(22), Integer.valueOf(23), Integer.valueOf(30), Integer.valueOf(31), Integer.valueOf(32) }).forEach(slot -> setButton(slot.intValue(), new MysteryUseButton(slot.intValue(), this.box)));
  }
}
