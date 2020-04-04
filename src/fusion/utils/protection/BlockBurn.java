package fusion.utils.protection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

/**
	 * 
	 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class BlockBurn implements Listener {
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent e) {
		
		e.setCancelled(true);
		
	}

}
