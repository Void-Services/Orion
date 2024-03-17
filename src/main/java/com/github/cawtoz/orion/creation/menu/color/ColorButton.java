package com.github.cawtoz.orion.creation.menu.color;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.lootbox.enums.ColorEnum;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.ItemBuilder;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ColorButton extends Button {

    private final ColorEnum color;
    private final Creation creation;

    public ColorButton(ColorEnum color, Creation creation) {
        this.color = color;
        this.creation = creation;

        ItemStack item = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setName("&5&lColor&8: &f" + color)
                .addLore("")
                .addLore("&5   ❱ &dclick &fto toggle. &5❰   ")
                .addLore("")
                .setColor(color.getColor())
                .build();

        if (creation.getColors().contains(color)) {
            item = new ItemBuilder(item)
                    .setName("&5&lColor&8: &a" + color)
                    .addEnchantment(Enchantment.DURABILITY)
                    .setGlow(true)
                    .build();
        }

        setItem(item);
        setDisplayName("&5&lColor&8: &f" + color);
        setLore(
                "",
                "&5    ❱ &dclick &fto accept. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        if (creation.getColors().contains(color)) {
            creation.getColors().remove(color);
        } else {
            creation.getColors().add(color);
        }
        new ColorMenu().open(player);
        CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play(player);
    }

}
