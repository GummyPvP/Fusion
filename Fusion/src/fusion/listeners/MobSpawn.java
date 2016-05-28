package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import fusion.utils.protection.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class MobSpawn implements Listener {
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		
		if (e.getSpawnReason() == SpawnReason.DISPENSE_EGG) return;
		
		if (RegionManager.getInstance().getRegion(e.getLocation().toVector()) != null) e.setCancelled(true);
		
	}

}
