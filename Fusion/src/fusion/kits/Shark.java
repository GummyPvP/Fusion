package fusion.kits;

import java.util.Collections;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
 * 
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Shark extends Kit {

	@Override
	public String getName() {
		return "Shark";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.BONE);
	}

	@Override
	public PotionEffect[] getPotionEffects() {

		return new PotionEffect[] { new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1),
				new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0) };

	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.GHAST_TEAR).name("&9Shark Tooth")
				.unsafeEnchant(Enchantment.DAMAGE_ALL, 5).lore("*Crunch*").build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] {
				new ItemBuilder(Material.LEATHER_HELMET).color(Color.AQUA).unsafeEnchant(Enchantment.OXYGEN, 0)
						.unsafeEnchant(Enchantment.WATER_WORKER, 2).build(),
				new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.AQUA).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.AQUA).build(),
				new ItemBuilder(Material.LEATHER_BOOTS).enchant(Enchantment.DEPTH_STRIDER, 3).color(Color.AQUA)
						.build() };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Moves faster in water!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {

		return 1000.0;

	}

	@Command(name = "Shark", description = "Gives the Shark kit.", usage = "/kit shark", inGameOnly = true)
	public void kitSharkCommand(CommandArgs args) {

		apply(args.getPlayer());

	}

}
