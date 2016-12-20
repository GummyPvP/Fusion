package fusion.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
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
		
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		
		if (e.getItemDrop().getItemStack().getType() == Material.BOWL) {
			
			e.getItemDrop().remove();
			
		} else e.setCancelled(true);
		
	}

}
