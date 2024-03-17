package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.config.Lang;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.CC;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class CancelButton extends Button {

    private final Creation creation;

    public CancelButton(Creation creation) {
        this.creation = creation;
        setItem(CMaterial.RED_WOOL.getItem());
        setDisplayName("&c&lCancel");
        setLore(
                "",
                "&5    ❱ &dclick &fto cancel. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        CSound.ENTITY_VILLAGER_NO.play(player);
        CC.send(player, Lang.CREATION_CANCEL.replace("%TYPE%", creation.getType().getName()));
        PUtil.remove(player);
        player.closeInventory();
    }

}
