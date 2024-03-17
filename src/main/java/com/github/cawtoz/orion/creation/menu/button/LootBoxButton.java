package com.github.cawtoz.orion.creation.menu.button;

import com.github.cawtoz.orion.Orion;
import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.CState;
import com.github.cawtoz.orion.creation.menu.BoxesMenu;
import com.github.cawtoz.orion.util.cawtoz.file.FileConfig;
import com.github.cawtoz.orion.lootbox.LootBox;
import com.github.cawtoz.orion.lootbox.LootBoxManager;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.profile.Profile;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class LootBoxButton extends Button {

    private final LootBox lootBox;

    public LootBoxButton(LootBox lootBox) {
        this.lootBox = lootBox;
        ItemStack itemStack = lootBox.getItem().clone();
        setItem(itemStack);
        setDisplayName("&5&l" + lootBox.getType().getName() + "Box&8: &f" + lootBox.getName());
        setLore(
                "",
                "&5    ❱ &dleft-click &fto edit. &5❰    ",
                "&5  ❱ &dright-click &fto remove. &5❰    ",
                ""
        );
    }

    @Override
    public void click(Player player, ClickType clickType) {
        switch (clickType) {
            case LEFT:
                CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play(player);
                Profile profile = PUtil.get(player);
                profile.setCreation(new Creation(player, lootBox));
                profile.getCreation().setState(CState.CREATING);
                break;
            case RIGHT:
                FileConfig file = new FileConfig(lootBox.getType().getFileName());
                file.set(lootBox.getName(), null);
                file.saveConfig();
                new LootBoxManager().loadBoxes();
                Orion.getInstance().loadCommands();
                new BoxesMenu().open(player);
        }
        CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play(player);
    }

}
