package fusion.utils.protection;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
	 * 
	 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerDamage implements Listener {
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		
		if (!(e.getEntity() instanceof Player)) return;
		
		Player player = (Player) e.getEntity();
		
		if (e.isCancelled()) return;
		
		List<Region> region = RegionManager.getInstance().getRegions(player.getLocation().toVector());
		
		if (region.isEmpty()) return;
		
		ProtectedRegion protectedRegion;
		
		protectedRegion = (ProtectedRegion) RegionManager.getInstance().getSmallestRegion(region);
		
		e.setCancelled(!protectedRegion.isPVPEnabled());
	}

}
