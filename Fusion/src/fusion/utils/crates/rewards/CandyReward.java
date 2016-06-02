package fusion.utils.crates.rewards;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.ItemBuilder;
import fusion.utils.chat.Chat;
import fusion.utils.crates.Reward;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class CandyReward extends Reward {

	private int candy;
	private int multiplier = 1;
	
	public CandyReward(int candies, int multiplier) {
		
		this.candy = candies;
		this.multiplier = multiplier;
		
	}
	
	@Override
	public String getName() {
		return "Candies";
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.EMERALD).name("&aCandy").lore("Currency to spend on kits and more!").build();
	}
	
	public int getMultiplier() {
		return multiplier;
	}
	
	public void setMultipler(int multiplier) {
		this.multiplier = multiplier;
	}
	
	public int getCandyAmount() {
		return candy * multiplier;
	}

	@Override
	public void apply(Player player) {
		
		Bukkit.broadcastMessage(Chat.SECONDARY_BASE + player.getName() + " won " + getCandyAmount() + " " + getName() + " from a crate! You can open a crate too with /vote!");
		
	}

}
