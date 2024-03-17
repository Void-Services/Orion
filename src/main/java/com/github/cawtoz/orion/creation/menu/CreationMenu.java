package com.github.cawtoz.orion.creation.menu;

import com.github.cawtoz.orion.creation.Creation;
import com.github.cawtoz.orion.creation.menu.button.*;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.profile.Profile;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.github.cawtoz.orion.util.cawtoz.menu.Menu;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
public class CreationMenu extends Menu {

    @Override
    protected void create(Player player) {

        setTitle("&5&lCreation Menu");
        setSize(9 * 5);
        setFill(CMaterial.BLACK_STAINED_GLASS_PANE.getItem());

        Profile profile = PUtil.get(player);
        Creation creation = profile.getCreation();

        if (profile.isEditing()) {
            setButton(11, new FireworkButton(creation));
            setButton(12, new LootButton(creation));
            setButton(14, new ItemButton(creation));
            setButton(15, new DisplayNameButton(creation));
        } else {
            setButton(10, new FireworkButton(creation));
            setButton(11, new NameButton(creation));
            setButton(12, new LootButton(creation));
            setButton(14, new ItemButton(creation));
            setButton(15, new TypeButton(creation));
            setButton(16, new DisplayNameButton(creation));
        }

        switch (creation.getType()) {
            case INSTANT:
                setButton(31, new AmountRewardButton(creation));
                break;
            case MYSTERY:
                setButton(31, new MaxUsesButton(creation));
        }

        setButton(28, new AcceptButton(creation));
        setButton(34, new CancelButton(creation));

    }

}
