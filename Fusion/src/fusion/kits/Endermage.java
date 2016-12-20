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
	 * Copyright GummyPvP. Created on Jun 5, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Endermage extends Kit {

	@Override
	public String getName() {
		return "Endermage";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.ENDER_PORTAL_FRAME);
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aEndermage Sword").lore("Beam me up, Scotty!").build();
		ItemStack hairyBalls = new ItemBuilder(Material.ENDER_PORTAL_FRAME).name("&5Endermage Teleporter").lore("Teleport the people above and below you directly to this portal!").build();
		
		return Arrays.asList(sword, hairyBalls);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Teleport people above and below you", "directly to anywhere you want!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 250;
	}

}
