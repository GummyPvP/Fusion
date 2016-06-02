package fusion.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		Player player = e.getPlayer();
		
		mKitUser.getInstance(player).unload();
		
		if (CombatLog.getInstance().isInCombat(player)) {
			
			player.setHealth(0);
			
			Bukkit.broadcastMessage(Chat.IMPORTANT_COLOR + player.getName() + Chat.BASE_COLOR + " logged out during a fight!");
			
		}
		
	}

}
