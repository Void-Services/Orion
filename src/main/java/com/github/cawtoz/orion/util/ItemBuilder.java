package com.github.cawtoz.orion.util;

import com.github.cawtoz.orion.util.cawtoz.CC;
import com.github.cawtoz.orion.util.cawtoz.CMaterial;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.AllArgsConstructor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ItemBuilder {

	private ItemStack itemStack;

	public ItemBuilder(Material material) {
		this(material, 1);
	}

	public ItemBuilder(Material material, int amount) {
		this(material, amount, (short) 0);
	}

	public ItemBuilder(Material material, int amount, short damage) {
		this(new ItemStack(material, amount, damage));
	}

	public ItemBuilder setType(Material material) {
		itemStack.setType(material);
		return this;
	}

	public ItemBuilder setAmount(int amount) {
		itemStack.setAmount(amount);
		return this;
	}

	public ItemBuilder setDurability(int durability) {
		itemStack.setDurability((short) durability);
		return this;
	}

	public ItemBuilder setData(MaterialData data) {
		itemStack.setData(data);
		return this;
	}

	public ItemBuilder setGlow(boolean glow) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		if (glow) {
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		} else {
			itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		itemStack.setItemMeta(itemMeta);
		return this;
	}


	public ItemBuilder addEnchantment(Enchantment enchantment) {
		addEnchantment(enchantment, 1);
		return this;
	}

	public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
		itemStack.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	public ItemBuilder removeEnchantment(Enchantment enchantment) {
		itemStack.removeEnchantment(enchantment);
		return this;
	}

	public ItemBuilder clearEnchantments() {
		for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
			itemStack.removeEnchantment(enchantment);
		}
		return this;
	}

	public ItemBuilder setName(String name) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(CC.format(name));
		itemStack.setItemMeta(itemMeta);
		return this;
	}

	public ItemBuilder addLore(String lore) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		List<String> lores = itemMeta.getLore();
		if (lores == null) {
			lores = Lists.newArrayList();
		}
			
		lores.add(CC.format(lore));

		itemMeta.setLore(lores);

		itemStack.setItemMeta(itemMeta);
		return this;
	}

	public ItemBuilder setLore(String... lores) {
		clearLore();

		for (String lore : lores) {
			addLore(lore);
		}

		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		clearLore();

		ItemMeta itemMeta = itemStack.getItemMeta();
		
		itemMeta.setLore(CC.format(lore));
		itemStack.setItemMeta(itemMeta);

		return this;
	}

	public ItemBuilder clearLore() {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setLore(Lists.newArrayList());
		
		itemStack.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemBuilder setColor(Color color) {
		if (itemStack.getItemMeta() instanceof LeatherArmorMeta) {
			LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
			leatherArmorMeta.setColor(color);
			itemStack.setItemMeta(leatherArmorMeta);
		}
		return this;
	}

	public ItemBuilder setPotionEffect(PotionEffect effect) {
		PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
		potionMeta.setMainEffect(effect.getType());
		potionMeta.addCustomEffect(effect, false);
		itemStack.setItemMeta(potionMeta);
		return this;
	}

	public ItemBuilder setPotionEffects(PotionEffect... effects) {
		PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
		potionMeta.setMainEffect(effects[0].getType());
		for (PotionEffect effect : effects) {
			potionMeta.addCustomEffect(effect, false);
		}
		itemStack.setItemMeta(potionMeta);
		return this;
	}

	public ItemBuilder setSkullOwner(String owner) {
		setTexture(getTexture(owner));
		return this;
	}

	public ItemBuilder setTexture(String value) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		if (itemMeta instanceof SkullMeta) {
			SkullMeta skullMeta = (SkullMeta) itemMeta;
			GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			profile.getProperties().put("textures", new Property("textures", value));
			try {
				Field profileField = skullMeta.getClass().getDeclaredField("profile");
				profileField.setAccessible(true);
				profileField.set(skullMeta, profile);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
			itemStack.setItemMeta(skullMeta);
		}
		return this;
	}

	public static String getTexture(String name) {
		try {
			URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
			String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();
			URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
			JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
			return textureProperty.get("value").getAsString();
		} catch (IOException e) {
			System.err.println("Could not get skin data from session servers!");
			e.printStackTrace();
			return "";
		}
	}

	public ItemStack build() {
		return itemStack;
	}
	
	public static String toBase64(ItemStack[] items) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(stream);
            bukkitStream.writeInt(items.length);
            for (int i = 0; i < items.length; ++i) {
                bukkitStream.writeObject(items[i]);
            }
            bukkitStream.close();
            return Base64Coder.encodeLines(stream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

	public static ItemStack[] fromBase64(String string) {
        try {
        	ByteArrayInputStream stream = new ByteArrayInputStream(Base64Coder.decodeLines(string));
            BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(stream);
            
            ItemStack[] items = new ItemStack[bukkitStream.readInt()];
            for (int i = 0; i < items.length; ++i) {
                items[i] = (ItemStack) bukkitStream.readObject();
            }
            bukkitStream.close();
            return items;
        }
        catch (ClassNotFoundException | IOException ex) {
			try {
				throw new IOException("Unable to decode class type.", ex);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
    }

}