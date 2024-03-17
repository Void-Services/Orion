package com.github.cawtoz.orion.lootbox.impl.mystery;

import com.github.cawtoz.orion.lootbox.LootBox;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.lootbox.impl.mystery.menu.MysteryMenu;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Setter @Getter
public class MysteryBox extends LootBox implements Cloneable {
  private int uses;
  private int maxUses;

  private HashMap<Integer, ItemStack> usedSlots = new HashMap<>();

  public MysteryBox(String name) {
    super(name, LootBoxType.MYSTERY);
  }
  
  public void use(HumanEntity player) {
    PUtil.get(player).setUsing(true);
    CSound.UI_BUTTON_CLICK.play(player);
    (new MysteryMenu(this)).open((Player)player);
  }
  
  public LootBox clone() {
    MysteryBox clonedLootBox = (MysteryBox)super.clone();
    clonedLootBox.usedSlots = new HashMap<>(this.usedSlots);
    return clonedLootBox;
  }

}
