package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import fusion.utils.editing.regions.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class MobSpawn implements Listener {
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		
		if (e.getSpawnReason() == SpawnReason.SPAWNER_EGG) return;
		
		if (!RegionManager.getInstance().getRegions(e.getLocation().toVector()).isEmpty()) e.setCancelled(true);
		
	}

}
