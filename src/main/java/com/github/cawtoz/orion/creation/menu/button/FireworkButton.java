package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.CState;
import com.github.cawtoz.orion.lootbox.enums.ColorEnum;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class FireworkButton extends Button {

    private final Creation creation;

    public FireworkButton(Creation creation) {
        this.creation = creation;
        setItem(CMaterial.FIREWORK_ROCKET.getItem());
        setDisplayName("&5&lColors&8:");
        for (ColorEnum color : creation.getColors()) addLore("&5&l・&f" + color);
        addLore(
                "",
                "&5    ❱ &dclick &fto edit. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        creation.setState(CState.COLOR);
        CSound.UI_BUTTON_CLICK.play(player);
    }

}
