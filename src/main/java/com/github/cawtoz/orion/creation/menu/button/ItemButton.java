package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.menu.CreationMenu;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemButton extends Button {

    public ItemButton(Creation creation) {
        ItemStack itemStack = creation.getItem().clone();
        setItem(itemStack);
        setDisplayName("&5&lItem&8: &f" + itemStack.getType());
        setLore(
                "",
                "&5 ❱ &dput the &fitem &dhere. &5❰ ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        new CreationMenu().open(player);

    }

}
