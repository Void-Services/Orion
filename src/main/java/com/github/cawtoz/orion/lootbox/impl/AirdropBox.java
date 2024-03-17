package com.github.cawtoz.orion.lootbox.impl;

import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.scheduler.BukkitTask;

import com.github.cawtoz.orion.Orion;
import com.github.cawtoz.orion.profile.PUtil;
import com.github.cawtoz.orion.lootbox.LootBox;
import com.github.cawtoz.orion.lootbox.enums.LootBoxType;
import com.github.cawtoz.orion.util.cawtoz.CSound;
import com.github.cawtoz.orion.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
@Getter @Setter
public class AirdropBox extends LootBox {

     public static final List<Dispenser> airdropsBlocks = new ArrayList<>();

    private ArmorStand airdrop;
    private final String headValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVlOTE1MmVmZDg5MmY2MGQ3ZTBkN2U1MzM2OWUwNDc3OWVkMzExMWUyZmIyNzUyYjZmNGMyNmRmNTQwYWVkYyJ9fX0=";
    private Location location;
    private BukkitTask task;

    private float yaw;
    private final double descentSpeed = -0.3;

    public AirdropBox(String name) {
        super(name, LootBoxType.AIRDROP);
    }

    @Override
    public void use(HumanEntity player) {
        removeItem(player);
        create(player.getLocation());
        task = Bukkit.getScheduler().runTaskTimer(Orion.getInstance(), () -> { makeAnimation(player); }, 0, 1L);
    }

    public void create(Location location) {
        location.setY(location.getY() + 15);
        airdrop = location.getWorld().spawn(location, ArmorStand.class);
        airdrop.setHelmet(new ItemBuilder(CMaterial.PLAYER_HEAD.getItem()).setTexture(headValue).build());
        airdrop.setVisible(false);
        airdrop.setGravity(false);
        this.location = airdrop.getLocation();
    }

    public void putDispenser() {
        location.add(0, 1, 0).getBlock().setType(Material.DISPENSER);
        Dispenser dispenser = (Dispenser) location.getBlock().getState();
        for (int i = 0; i < 9; i++) dispenser.getInventory().addItem(getRandomItem());
        airdropsBlocks.add(dispenser);
        playFirework(airdrop.getLocation());
    }

    public void destroy() {
        airdrop.remove();
        task.cancel();
    }

    public void makeAnimation(HumanEntity player) {

        //Rotation
        yaw += (float) 360 / (5 * 20);
        if (yaw >= 180) yaw = -180;
        location.setYaw(yaw);

        //Displacement
        location.add(0, descentSpeed, 0);
        airdrop.teleport(location);

        //Particle and sound
        location.getWorld().playEffect(location.clone().add(0, 4, 0.5), Effect.SMOKE, 1);
        CSound.UI_BUTTON_CLICK.play(location);

        //Finish
        if (location.getBlock().getType() != Material.AIR) {
            destroy();
            putDispenser();
            PUtil.remove(player);
        }

    }

}
