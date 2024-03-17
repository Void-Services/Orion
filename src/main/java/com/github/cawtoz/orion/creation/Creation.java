package com.github.cawtoz.orion.creation;

import com.github.cawtoz.orion.creation.menu.BoxesMenu;
import com.github.cawtoz.orion.creation.menu.color.ColorMenu;
import com.github.cawtoz.orion.lootbox.impl.mystery.MysteryBox;
import com.github.cawtoz.orion.lootbox.impl.mystery.MysteryBox;
import com.github.cawtoz.orion.util.cawtoz.menu.Menu;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.ClickType;

import com.github.cawtoz.orion.creation.menu.CreationMenu;
import com.github.cawtoz.orion.config.Lang;
import com.github.cawtoz.orion.util.cawtoz.file.FileConfig;
import com.github.cawtoz.orion.lootbox.*;
import com.github.cawtoz.orion.lootbox.impl.*;
import com.github.cawtoz.orion.lootbox.enums.*;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.util.cawtoz.CC;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.ItemBuilder;
import com.github.cawtoz.orion.Orion;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Creation {

    private final HumanEntity humanEntity;
    private CState state;
    private String name;
    private LootBoxType type;
    private String displayName;
    private ItemStack item;
    private Inventory loot;
    private final List<ColorEnum> colors = new ArrayList<>();
    private int amountReward;
    private int maxUses;

    public Creation(HumanEntity humanEntity) {
        this.humanEntity = humanEntity;
        type = LootBoxType.INSTANT;
        displayName = "";
        item = new ItemBuilder(Material.ENDER_CHEST)
                .setLore(
                        "",
                        "&5   ❱ &dleft-click &fto view loot. &5❰   ",
                        "&5     ❱ &dright-click &fto use. &5❰",
                        "",
                        "%LOOT%",
                        ""
                )
                .build();
        loot = Bukkit.createInventory(null, 9 * 6, CC.format(displayName + " Loot"));
        colors.add(ColorEnum.PURPLE);
        colors.add(ColorEnum.MAGENTA);
        amountReward = 3;
        maxUses = 3;
        PUtil.get(humanEntity).setCreation(this);
    }

    public Creation(HumanEntity humanEntity, LootBox lootBox) {
        this.humanEntity = humanEntity;
        name = lootBox.getName();
        type = lootBox.getType();
        displayName = lootBox.getDisplayName();
        item = lootBox.getItem();
        loot = lootBox.getLoot();
        lootBox.getColors().forEach(color -> {
            for (ColorEnum colorEnum : ColorEnum.values()) {
                if (colorEnum.getColor().equals(color)) colors.add(colorEnum);
            }
        });
        switch (lootBox.getType()) {
            case INSTANT:
                amountReward = ((InstantBox) lootBox).getAmountReward();
                break;
            case MYSTERY:
                maxUses = ((MysteryBox) lootBox).getMaxUses();
        }
    }

    public void setState(CState state) {

        this.state = state;

        switch (state) {
            case EDITING:
                new BoxesMenu().open((Player) humanEntity);
                break;
            case CREATING:
                CSound.BLOCK_CHEST_CLOSE.play(humanEntity);
                Bukkit.getScheduler().runTask(Orion.getInstance(), () -> { new CreationMenu().open((Player) humanEntity); });
                break;
            case STOPPED:
                CC.send(humanEntity, Lang.CREATION_RETURN);
                break;
            case NAME:
                CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play(humanEntity);
                humanEntity.closeInventory();
                CC.send(humanEntity, Lang.CREATION_EDITING_NAME.replace("%TYPE%", type.getName()));
                break;
            case DISPLAY_NAME:
                CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play(humanEntity);
                humanEntity.closeInventory();
                CC.send(humanEntity, Lang.CREATION_EDITING_DISPLAY_NAME.replace("%TYPE%", type.getName()));
                break;
            case LOOT:
                CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play(humanEntity);
                Inventory inventory = Bukkit.createInventory(null, 9 * 6, CC.format(displayName + " Loot"));
                inventory.setContents(loot.getContents());
                loot = inventory;
                Menu.setCancelCloseEvent(true);
                humanEntity.openInventory(loot);
                Menu.setCancelCloseEvent(false);
                break;
            case COLOR:
                CSound.ENTITY_EXPERIENCE_ORB_PICKUP.play(humanEntity);
                new ColorMenu().open((Player) humanEntity);
        }

    }

    public boolean isState(CState state) {
        return this.state == state;
    }

    public void create() {

        if (LootBoxManager.getBox(name, type) != null && !PUtil.get(humanEntity).isEditing()) {
            CSound.ENTITY_VILLAGER_NO.play(humanEntity);
            String message = Lang.CREATION_EXIST
                    .replace("%TYPE%", type.getName())
                    .replace("%NAME%", name);
            CC.send(humanEntity, message);
            return;
        }

        FileConfig file = new FileConfig(type.getFileName());
        CSound.BLOCK_NOTE_BLOCK_PLING.play(humanEntity);
        CC.send(humanEntity, Lang.CREATION_ACCEPT.replace("%TYPE%", type.getName()));

        file.set(name + ".DISPLAY_NAME", displayName);
        if (type == LootBoxType.INSTANT) file.set(name + ".AMOUNT_REWARD", amountReward);
        if (type == LootBoxType.MYSTERY) file.set(name + ".MAX_USES", maxUses);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        item.setItemMeta(itemMeta);
        file.setItem(name + ".ITEM", item);
        file.set(name + ".LOOT", ItemBuilder.toBase64(loot.getContents()));

        List<Color> bukkitColors = new ArrayList<>();
        List<Color> configColors = file.getColors(name + ".COLORS");

        for (ColorEnum color : colors) {
            bukkitColors.add(color.getColor());
        }

        for (Color color : configColors) {
            if (!ColorEnum.getColors().contains(color)) {
                bukkitColors.add(color);
            }
        }

        file.set(name + ".COLORS", null);
        for (int i = 0; i < bukkitColors.size(); i++) file.set(name + ".COLORS." + i, bukkitColors.get(i).serialize());

        file.saveConfig();
        new LootBoxManager().loadBoxes();
        Orion.getInstance().loadCommands();

    }

    public void editAmountReward(ClickType clickType) {
        switch (clickType) {
            case LEFT:
                amountReward += 1;
                break;
            case RIGHT:
                if (amountReward <= 1) {
                    CSound.ENTITY_VILLAGER_NO.play(humanEntity);
                    return;
                } else {
                    amountReward -= 1;
                }
        }
        CSound.UI_BUTTON_CLICK.play(humanEntity);
    }

    public void editMaxUses(ClickType clickType) {
        switch (clickType) {
            case LEFT:
                if (maxUses >= 9) {
                    CSound.ENTITY_VILLAGER_NO.play(humanEntity);
                    return;
                }
                maxUses += 1;
                break;
            case RIGHT:
                if (maxUses <= 1) {
                    CSound.ENTITY_VILLAGER_NO.play(humanEntity);
                    return;
                } else {
                    maxUses -= 1;
                }
        }
        CSound.UI_BUTTON_CLICK.play(humanEntity);
    }

    public void toggleType(ClickType clickType) {
        CSound.UI_BUTTON_CLICK.play(humanEntity);
        LootBoxType[] types = LootBoxType.values();
        for (int i = 0; i < types.length; i++) {
            if (types[i] == type) {
                switch (clickType) {
                    case LEFT:
                        if (i == types.length - 1) i = -1;
                        type = types[++i];
                        return;
                    case RIGHT:
                        if (i == 0)  i = types.length;
                        type = types[--i];
                        return;
                }
            }
        }
    }

}
