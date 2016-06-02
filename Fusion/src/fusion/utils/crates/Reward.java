package fusion.utils.crates;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.chat.Chat;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class Reward {
	
	public abstract String getName();
	
	public abstract ItemStack getItem();
	
	public void apply(Player player) {
		
		Bukkit.broadcastMessage(Chat.SECONDARY_BASE + player.getName() + " won " + getName() + " from a crate! You can open a crate too with /vote!");
		
	}

}
