package fusion.utils.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.ItemBuilder;

/**
 * 
 * Copyright GummyPvP. Created on Jun 3, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class CandyGUI extends GUI {
	
	public static final String INVENTORY_NAME = ChatColor.GREEN + "GummyPvP - Candy Man";

	public CandyGUI(Player player) {
		super(45, INVENTORY_NAME, player);
		
		player.openInventory(getInventory());
		
	}

	@Override
	public void populateInventory() {
		
		ItemStack buyKits = new ItemBuilder(Material.WATCH).name("&6Purchase Kits (in-game currency)").build();
		
		ItemStack playerGemReward = new ItemBuilder(Material.IRON_INGOT).name("&aDaily: 250 Gems").build();
		ItemStack slimeGemReward = new ItemBuilder(Material.GOLD_INGOT).name("&aDaily: 250 Gems (Slime Rank)").build();
		ItemStack hariboGemReward = new ItemBuilder(Material.DIAMOND).name("&aDaily: 250 Gems (Haribo Rank)").build();
		ItemStack gummyGemReward = new ItemBuilder(Material.EMERALD).name("&aDaily: 250 Gems (Gummy Rank)").build();
		
		getInventory().setItem(8, buyKits);
		getInventory().setItem(10, playerGemReward);
		getInventory().setItem(12, slimeGemReward);
		getInventory().setItem(14, hariboGemReward);
		getInventory().setItem(16, gummyGemReward);
		
		// 11 15 22
		
		ItemStack comingSoon = new ItemBuilder(Material.WOOL).name("&4Coming Soon!").build();
		ItemStack vote = new ItemBuilder(Material.TRIPWIRE_HOOK).name("&aVote!").lore("Click to vote and get free rewards!").build();
		
		getInventory().setItem(20, comingSoon);
		getInventory().setItem(24, comingSoon);
		
		getInventory().setItem(31, vote);
		
	}
}
