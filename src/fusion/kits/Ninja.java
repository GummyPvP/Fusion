package fusion.kits;

import java.util.Arrays;
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

public class Ninja extends Kit {

	@Override
	public String getName() {
		return "Ninja";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.ENDER_EYE);
	}

	@Override
	public PotionEffect[] getPotionEffects() {

		return new PotionEffect[] { new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)};

	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.DIAMOND_HOE).name("&aJagged Dagger").lore("All warfare is based on deception").unsafeEnchant(Enchantment.DAMAGE_ALL, 3).build();
		ItemStack eye = new ItemBuilder(Material.ENDER_PEARL).name("&aEye of Darkness").amount(4).lore("Poof!").build();

		return Arrays.asList(sword, eye);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemBuilder(Material.LEATHER_HELMET).color(Color.BLACK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(), new ItemBuilder(Material.LEATHER_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).color(Color.BLACK).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).color(Color.BLACK).build(), new ItemBuilder(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).color(Color.BLACK).build() };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Can teleport, and has a chance to blind the enemy!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 750.0;

	}

	@Command(name = "Ninja", description = "Gives the ninja kit.", usage = "/kit ninja", inGameOnly = true)
	public void kitNinjaCommand(CommandArgs args) {
		
		apply(args.getPlayer());

	}

}
