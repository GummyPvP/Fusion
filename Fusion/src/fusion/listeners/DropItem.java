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
	
	Material[] allowedMaterials = new Material[] { Material.BOWL };
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		
		for (Material material : allowedMaterials) {
			
			if (e.getItemDrop().getItemStack().getType() != material) e.getItemDrop().remove();
			
		}
		
	}

}
