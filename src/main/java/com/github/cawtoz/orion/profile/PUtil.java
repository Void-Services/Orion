package com.github.cawtoz.orion.profile;

import org.bukkit.entity.HumanEntity;

import java.util.*;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class PUtil {

    private static final Map<HumanEntity, Profile> profiles = new HashMap<>();

    public static Profile get(HumanEntity target) {
        return profiles.get(target);
    }

    public static Profile create(HumanEntity target) {
        profiles.put(target, new Profile(target.getUniqueId()));
        return get(target);
    }

    public static Profile getOrCreate(HumanEntity target) {
        return (PUtil.get(target) == null) ? PUtil.create(target) : PUtil.get(target);
    }

    public static void remove(HumanEntity target) {
        profiles.remove(target);
    }

}
