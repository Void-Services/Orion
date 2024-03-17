package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.menu.CreationMenu;
import com.github.cawtoz.orion.util.ItemBuilder;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MaxUsesButton extends Button {

    private final Creation creation;

    public MaxUsesButton(Creation creation) {
        this.creation = creation;
        setItem(
                new ItemBuilder(CMaterial.PLAYER_HEAD.getItem())
                        .setDurability(3)
                        .setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyZmU4ODFmYmI3MzM2MmM1NmM0MWUxZDE0MDkxZDZkMmY3YjUxYzI4NjI1ZmIyN2Y4OTA3YzM2NmVmOGYxNCJ9fX0=")
                        .build()
        );
        setDisplayName("&5&lMaxUses&8: &f" + creation.getMaxUses());
        setLore(
                "",
                "&5    ❱ &dclick &fto edit. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        creation.editMaxUses(clickType);
        new CreationMenu().open(player);
    }

}
