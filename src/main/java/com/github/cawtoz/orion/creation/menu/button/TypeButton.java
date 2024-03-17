package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.menu.BoxesMenu;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.creation.menu.CreationMenu;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class TypeButton extends Button {

    Creation creation;

    public TypeButton(Creation creation) {
        this.creation = creation;
        setItem(CMaterial.COMMAND_BLOCK.getItem());
        setDisplayName("&5&lType&8: &f" + creation.getType().getName());
        addLore("");
        for (LootBoxType type : LootBoxType.values()) {
            if (creation.getType() == type) {
                addLore("&5&l・&a" + type.getName());
                continue;
            }
            addLore("&5&l・&f" + type.getName());
        }
        addLore(
                "",
                "&5    ❱ &dclick &fto edit. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        creation.toggleType(clickType);
        if (PUtil.get(player).isEditing()) new BoxesMenu().open(player);
        else new CreationMenu().open(player);
    }

}
