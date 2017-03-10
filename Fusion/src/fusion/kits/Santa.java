package fusion.kits;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
 * 
 * Copyright GummySanta. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Santa extends Kit {

	@Override
	public String getName() {
		return "Santa";
	}

	@Override
	public ItemStack getInventoryItem() {

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

		SkullMeta meta = (SkullMeta) skull.getItemMeta();

		meta.setOwner("Santa");

		skull.setItemMeta(meta);

		return skull;
	}

	@Override
	public PotionEffect[] getPotionEffects() {

		return new PotionEffect[] { new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0), new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1), new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1)};

	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aSanta's Sword").lore("u wot m8").build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

		SkullMeta meta = (SkullMeta) skull.getItemMeta();

		meta.setOwner("Santa");

		skull.setItemMeta(meta);

		return new ItemStack[] { skull, new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.RED).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.GREEN).build(),
				new ItemBuilder(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.color(Color.GREEN).build() };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Look like and feel like santa!" };
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public double getCost() {
		return 0.0;

	}

	@Command(name = "Santa", description = "Gives the Santa kit.", usage = "/kit Santa", inGameOnly = true)
	public void kitSantaCommand(CommandArgs args) {

		apply(args.getPlayer());

	}

}
