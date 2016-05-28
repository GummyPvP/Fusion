package fusion.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class BlockBreak implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		
		if (player.getGameMode() == GameMode.CREATIVE) return;
		
		for (Region regions : RegionManager.getInstance().getRegions()) {
			
			if (regions instanceof ProtectedRegion) {
				
				ProtectedRegion region = (ProtectedRegion) regions;
				
				if (region.getBounds().inBounds(e.getBlock().getLocation())) {
					
					e.setCancelled(true);
					
				}
				
			}
			
		}
		
	}

}
