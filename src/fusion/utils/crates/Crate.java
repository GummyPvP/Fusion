package fusion.utils.crates;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fusion.utils.chat.Chat;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class Crate {
	
	public abstract String getName();
	
	public abstract List<Reward> getRewards();
	
	public void apply(Player player) {
		
		Random r = new Random();
		
		getRewards().get(r.nextInt(getRewards().size())).apply(player);
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			
			Chat.getInstance().messagePlayer(online, ChatColor.GOLD + player.getName() + " opened a " + getName() + " crate! Type /vote to win something too!");
			
		}
		
	}

}
