package com.github.cawtoz.orion.util.cawtoz;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public enum CSound {

    ENTITY_EXPERIENCE_ORB_PICKUP("ORB_PICKUP"),
    ENTITY_GENERIC_EXPLODE("EXPLODE"),
    BLOCK_CHEST_OPEN("CHEST_OPEN", "ENTITY_CHEST_OPEN"),
    BLOCK_CHEST_CLOSE("CHEST_CLOSE", "ENTITY_CHEST_CLOSE"),
    ENTITY_PLAYER_LEVELUP("LEVEL_UP"),
    ENTITY_VILLAGER_NO("VILLAGER_NO"),
    BLOCK_NOTE_BLOCK_PLING("NOTE_PLING", "BLOCK_NOTE_PLING"),
    UI_BUTTON_CLICK("CLICK"),
    ENTITY_ITEM_PICKUP("ITEM_PICKUP");

    private String[] sounds;
    private final String soundName;

    CSound(String... soundNames) {
        this.sounds = soundNames;
        soundName = lookSoundName();
    }

    public void play(HumanEntity humanEntity) {
        Player player = (Player) humanEntity;
        if (soundName != null) player.playSound(player.getLocation(), Sound.valueOf(soundName), 1, 1);
    };

    public void play(Location location) {
        if (soundName != null) location.getWorld().playSound(location, Sound.valueOf(soundName), 1, 1);
    };

    private String lookSoundName() {
        if (parseSound(name()) != null) return name();
        for (String soundName : sounds) {
            if (parseSound(soundName) != null) return soundName;
        }
        return null;
    }

    private Sound parseSound(String soundName) {
        try {
            return Sound.valueOf(soundName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
