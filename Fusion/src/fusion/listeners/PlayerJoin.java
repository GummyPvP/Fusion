package fusion.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fusion.main.Main;
import fusion.utils.Utils;
import fusion.utils.mKitUser;

/**
	 * 
	 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		final Player player = e.getPlayer();
		
		mKitUser.getInstance(player).load();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				
				Utils.giveDefaultItems(player);
				
			}
			
		}, 15L);
		
	}

}
