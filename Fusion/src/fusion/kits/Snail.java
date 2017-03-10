package fusion.kits;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Snail extends Kit {

	@Override
	public String getName() {
		return "Snail";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new Potion(PotionType.SLOWNESS).toItemStack(1);
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack sword = new ItemBuilder(Material.IRON_AXE).name("&aGhetto Axe").lore(Collections.singletonList("Made from ye' own shell!")).enchant(Enchantment.DAMAGE_ALL, 1).build();
		
		return Collections.singletonList(sword);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemBuilder(Material.LEATHER_HELMET).color(Color.WHITE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(), new ItemBuilder(Material.LEATHER_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).color(Color.WHITE).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).color(Color.WHITE).build(), new ItemBuilder(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).color(Color.WHITE).build() };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}
	
	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "1/3 chance of slowing someone with every hit!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}
	
	@Override
	public double getCost() {
		
		return 500.0;
		
	}
	
	@Command(name = "Snail", description = "Gives you the Snail kit", usage = "/kit snail", inGameOnly = true)
	public void kitSnailCommand(CommandArgs args) {
		
		apply(args.getPlayer());
		
	}
	
}
