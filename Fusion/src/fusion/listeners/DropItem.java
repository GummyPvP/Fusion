package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
	 * 
	 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class DropItem implements Listener {
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		
		e.setCancelled(true);
		
	}

}
