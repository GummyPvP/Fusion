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

public class Switcher extends Kit {

	@Override
	public String getName() {
		return "Switcher";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.SNOWBALL);
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aSwitcher Sword").lore("Swip swop swippity swop").build();
		ItemStack hairyBalls = new ItemBuilder(Material.SNOWBALL).name("&5Switcher Device").lore("Throw me to switch positions with people!").amount(16).build();
		
		return Arrays.asList(sword, hairyBalls);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Throw snowballs to switch positions with others!" };
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public double getCost() {
		return 0;
	}

}
