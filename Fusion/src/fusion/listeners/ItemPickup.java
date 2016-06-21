package fusion.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import fusion.utils.mKitUser;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class ItemPickup implements Listener {
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		
		Player player = e.getPlayer();
		
		if (mKitUser.getInstance(player).hasKit()) return;
		
		e.setCancelled(true);
		
	}

}
