package fusion.utils;

import org.bukkit.inventory.ItemStack;

/**
	 * 
	 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class ItemUtils {
	
	public static boolean isHelmet(ItemStack item) {
		
		return (item.getType().toString().toLowerCase().contains("helmet"));
		
	}
	
	public static boolean isChestplate(ItemStack item) {
		
		return (item.getType().toString().toLowerCase().contains("chestplate"));
		
	}
	
	public static boolean areLeggings(ItemStack item) {
		
		return (item.getType().toString().toLowerCase().contains("leggings"));
		
	}
	
	public static boolean areBoots(ItemStack item) {
		
		return (item.getType().toString().toLowerCase().contains("boots"));
		
	}

}
