package fusion.utils.protection;

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
		
		Region region = RegionManager.getInstance().getRegion(player.getLocation().toVector());
		
		if (region == null) return;
		
		if (!(region instanceof ProtectedRegion)) return;
		
		e.setCancelled(!((ProtectedRegion) region).isPVPEnabled());
	}

}
