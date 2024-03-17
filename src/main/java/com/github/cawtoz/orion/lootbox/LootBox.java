package com.github.cawtoz.orion.lootbox;

import com.github.cawtoz.orion.config.Config;
import com.github.cawtoz.orion.config.Lang;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.profile.Profile;
import com.github.cawtoz.orion.util.cawtoz.CC;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.cawtoz.InvUtil;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.FireworkMeta;
import com.github.cawtoz.orion.Orion;
import com.github.cawtoz.orion.profile.PUtil;

import java.util.*;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
@Getter @Setter
public abstract class LootBox implements Cloneable {

    public static final Map<LootBoxType, List<Player>> blackListUse = new HashMap<>();
    public static final Map<LootBoxType, List<Player>> blackListView = new HashMap<>();

    static {
        for (LootBoxType type : LootBoxType.values()) {
            blackListUse.put(type, new ArrayList<>());
            blackListView.put(type, new ArrayList<>());
        }
    }

    private final LootBoxType type;
    private final String name;
    private String displayName;
    private ItemStack item;
    private ItemStack giveItem;
    private Inventory loot;
    private List<Color> colors;
    private List<ItemStack> reward = new ArrayList<>();

    public LootBox(String name, LootBoxType type) {
        this.type = type;
        this.name = name;
    }

    public void setNBT() {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("name", name);
        nbtItem.setString("type", type.name());
        nbtItem.applyNBT(item);
    }

    public void give(HumanEntity player, int amount) {
        for (int i = 0; i < amount; i++) InvUtil.addItem(player, giveItem);
    }

    public void giveAll(int amount) {
        Bukkit.getOnlinePlayers().forEach(player -> give(player, amount));
    }

    public void setLoot(ItemStack[] loot) {
        this.loot = Bukkit.createInventory(null, 9 * 6, CC.format(displayName));
        this.loot.setContents(loot);
    }

    public void open(HumanEntity player, Action action) {

        Profile profile = PUtil.get(player);
        if (profile != null) if (profile.isLooking() || profile.isUsing()) return;

        if (shouldPerformAction(action, "VIEW") && !blackListView.get(type).contains(player)) {
            view(player);
        }

        if (shouldPerformAction(action, "USE") && !blackListUse.get(type).contains(player)) {
            if (InvUtil.hasItems(getLoot())) {
                use(player);
            } else {
                CC.send(player, Lang.USE_NO_LOOT.replace("%TYPE%", type.getName()));
            }
        }

    }

    private boolean shouldPerformAction(Action action, String actionType) {
        switch (actionType) {
            case "VIEW" : return Config.LOOT_BOX_VIEW_ENABLE && shouldClick(action, Config.LOOT_BOX_VIEW_CLICK);
            case "USE" : return Config.LOOT_BOX_USE_ENABLE && shouldClick(action, Config.LOOT_BOX_USE_CLICK);
            default: return false;
        }
    }

    private boolean shouldClick(Action action, String clickType) {
        switch (clickType) {
            case "LEFT" : return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
            case "RIGHT" : return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
            default : return false;
        }
    }

    public void view(HumanEntity player) {
        PUtil.get(player).setLooking(true);
        player.openInventory(loot);
    }

    public abstract void use(HumanEntity player);

    public void playFirework(Location location) {
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(colors).flicker(true).build());
        fireworkMeta.setPower(1);
        firework.setFireworkMeta(fireworkMeta);
        int detonations = 5;
        Bukkit.getScheduler().runTaskLater(Orion.getInstance(), () -> { for (int i = 0; i < detonations; i++) firework.detonate(); }, 5L);
    }

    public ItemStack getRandomItem() {
        ItemStack item;
        do {
            item = loot.getContents()[new Random().nextInt(getLoot().getSize())];
        } while(item == null);
        return item;
    }

    public void removeItem(HumanEntity player) {
        player.getInventory().removeItem(giveItem);
    }

    public void finish(HumanEntity player) {
        InvUtil.addItems(player, reward);
        playFirework(player.getLocation());
        CSound.ENTITY_PLAYER_LEVELUP.play(player);
        PUtil.remove(player);
    }

    @Override
    public LootBox clone() {
        try {
            LootBox clonedLootBox = (LootBox) super.clone();
            clonedLootBox.reward = new ArrayList<>(this.reward);
            return clonedLootBox;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
