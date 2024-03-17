package com.github.cawtoz.orion.util.cawtoz.menu.paginated;

import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class PreviousPageButton extends Button {

    PaginatedMenu paginatedMenu;

    public PreviousPageButton(PaginatedMenu paginatedMenu) {
        this.paginatedMenu = paginatedMenu;
        setMaterial(Material.ARROW);
        setDisplayName(ChatColor.GREEN + "&ePrevious Page");
    }

    @Override
    public void click(Player player, ClickType clickType) {
        paginatedMenu.previousPage(player);
        paginatedMenu.open(player);
        CSound.UI_BUTTON_CLICK.play(player);
    }

}
