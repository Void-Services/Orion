package com.github.cawtoz.orion.lootbox.impl;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.entity.HumanEntity;

import com.github.cawtoz.orion.lootbox.LootBox;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
@Getter @Setter
public class InstantBox extends LootBox {

    private int amountReward;

    public InstantBox(String name) {
        super(name, LootBoxType.INSTANT);
    }

    @Override
    public void use(HumanEntity player) {
        for (int i = 0; i < amountReward; i++) getReward().add(getRandomItem());
        removeItem(player);
        finish(player);
    }

}
