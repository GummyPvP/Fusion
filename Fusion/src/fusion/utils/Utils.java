package fusion.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.main.Main;

/**
	 * 
	 * Copyright GummyPvP. Created on May 24, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Utils {
	
	public static void giveDefaultItems(Player player) {
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				
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
			
		}, 15L);
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
