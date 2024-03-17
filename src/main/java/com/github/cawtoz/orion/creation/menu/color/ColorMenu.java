package com.github.cawtoz.orion.creation.menu.color;

import com.github.cawtoz.orion.lootbox.enums.ColorEnum;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
@AllArgsConstructor
public class ColorMenu extends Menu {

    @Override
    protected void create(Player player) {
        setTitle("&5&lColors");
        setSize(9 * 6);
        for (ColorEnum color : ColorEnum.values()) setButton(getButtons().size(), new ColorButton(color, PUtil.get(player).getCreation()));
    }

}
