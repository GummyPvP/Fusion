package fusion.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import fusion.main.Main;
import fusion.utils.Utils;

/**
	 * 
	 * Copyright GummyPvP. Created on May 24, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerRespawn implements Listener {
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		
		final Player player = e.getPlayer();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				
				Utils.giveDefaultItems(player);
				
			}
			
		}, 15L);
		
	}

}
