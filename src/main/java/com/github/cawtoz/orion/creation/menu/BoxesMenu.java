package com.github.cawtoz.orion.creation.menu;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.menu.button.LootBoxButton;
import com.github.cawtoz.orion.creation.menu.button.TypeButton;
import com.github.cawtoz.orion.lootbox.LootBox;
import com.github.cawtoz.orion.lootbox.LootBoxManager;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.github.cawtoz.orion.util.cawtoz.menu.paginated.PaginatedMenu;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class BoxesMenu extends PaginatedMenu {

    @Override
    protected void create(Player player) {

        setTitle("&5&lLootBoxes Menu");
        setSize(9 * 6);
        setFill(CMaterial.BLACK_STAINED_GLASS_PANE.getItem());

        Creation creation = PUtil.get(player).getCreation();
        setButton(getSize() - 5, new TypeButton(creation));
        List<LootBox> boxes = LootBoxManager.getBoxes(creation.getType());
        boxes.forEach(lootBox -> { addPageButton(new LootBoxButton(lootBox)); });

    }

}
