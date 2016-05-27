package fusion.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
	 * 
	 * Copyright GummyPvP. Created on May 24, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Utils {
	
	public static void giveDefaultItems(Player player) {
		
		player.getInventory().clear();
		
		mKitUser user = mKitUser.getInstance(player);
		
		ItemStack kitSelector = new ItemBuilder(Material.NETHER_STAR).name("&aKit Selector").lore("Click me to show your owned kits!").build();
		ItemStack cosmeticSelector = new ItemBuilder(Material.CHEST).name("&aCosmetic Selector").lore("Click me to change cosmetic settings!").build();
		ItemStack warpSelector = new ItemBuilder(Material.COMPASS).name("&aWarps").lore("Click me to show cool warps!").build();
		
		player.getInventory().setItem(0, kitSelector);
		player.getInventory().setItem(4, cosmeticSelector);
		player.getInventory().setItem(8, warpSelector);
		
		if (user.hasPreviousKit()) {
			
			ItemStack previousKit = new ItemBuilder(user.getPreviousKit().getInventoryItem()).name("&5Previous Kit: &a" + user.getPreviousKit().getName()).lore("Click to use your previous kit!").build();
			player.getInventory().addItem(previousKit);
			
		}
		
	}

}
