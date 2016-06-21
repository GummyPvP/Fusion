package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;

/**
	 * 
	 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Thor extends Kit {

	@Override
	public String getName() {
		return "Thor";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.WOOD_AXE);
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack sword = new ItemBuilder(Material.STONE_SWORD).name("&aThor Sword").lore("I'm a thorry thor thorring thorily").build();
		ItemStack axe = new ItemBuilder(Material.WOOD_AXE).name("&5Thor Axe").lore("Call lightning from the sky!").build();
		
		return Arrays.asList(sword, axe);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS) };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public String getSpecialAdvantageString() {
		return "Call lightning from the sky!";
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 500.0;
	}
}
