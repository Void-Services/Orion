package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.menu.CreationMenu;
import com.github.cawtoz.orion.util.ItemBuilder;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class AmountRewardButton extends Button {

    private final Creation creation;

    public AmountRewardButton(Creation creation) {
        this.creation = creation;
        setItem(
                new ItemBuilder(CMaterial.PLAYER_HEAD.getItem())
                        .setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGRkNDI0MDA2MDJkYjNlYTQ3ZDVjNmQyNDEzYTgxMzVlYjRkN2YzZmEyZjIyNjE3ZWYxYmNiMGM3MGIwNGU2MCJ9fX0=")
                        .build()
        );
        setDisplayName("&5&lAmountReward&8: &f" + creation.getAmountReward());
        setLore(
                "",
                "&5    ❱ &dclick &fto edit. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        creation.editAmountReward(clickType);
        new CreationMenu().open(player);
    }

}
