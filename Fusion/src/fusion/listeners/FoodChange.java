package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
	 * 
	 * Copyright GummyPvP. Created on May 20, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class FoodChange implements Listener {
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		
		e.setCancelled(true);
		
	}

}
