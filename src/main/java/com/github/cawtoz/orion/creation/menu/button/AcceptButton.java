package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class AcceptButton extends Button {

    private final Creation creation;

    public AcceptButton(Creation creation) {
        this.creation = creation;
        setItem(CMaterial.LIME_WOOL.getItem());
        setDisplayName("&a&lAccept");
        setLore(
                "",
                "&5    ❱ &dclick &fto accept. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        creation.create();
        PUtil.remove(player);
        player.closeInventory();
    }

}
