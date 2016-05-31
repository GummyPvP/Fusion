package fusion.utils.protection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

/**
	 * 
	 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class BlockDecay implements Listener {
	
	@EventHandler
	public void onBlockDecay(LeavesDecayEvent e) {
		
		e.setCancelled(true);
		
	}

}
