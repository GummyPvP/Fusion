package fusion.utils.protection;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

/**
	 * 
	 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class BlockIgnite implements Listener {
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e) {
		
		if (e.getCause() == IgniteCause.FLINT_AND_STEEL && e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		
		e.setCancelled(true);
		
	}

}
