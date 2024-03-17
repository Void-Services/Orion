package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.CState;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class NameButton extends Button {

    private final Creation creation;

    public NameButton(Creation creation) {
        this.creation = creation;
        setMaterial(Material.PAPER);
        setDisplayName("&5&lName&8: &f" + creation.getName());
        setLore(
                "",
                "&5    ❱ &dclick &fto edit. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        CSound.BLOCK_CHEST_CLOSE.play(player);
        creation.setState(CState.NAME);
    }

}
