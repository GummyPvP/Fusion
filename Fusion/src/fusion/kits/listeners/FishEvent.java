package fusion.kits.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import fusion.utils.mKitUser;
import fusion.utils.editing.regions.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 20, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class FishEvent implements Listener {
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		
		if (!(e.getCaught() instanceof Player)) return;
		if (!mKitUser.getInstance(e.getPlayer()).hasKit()) return;
		if (!mKitUser.getInstance(e.getPlayer()).getKit().getName().equalsIgnoreCase("Fisherman")) return;
		
		if (RegionManager.getInstance().isInProtectedRegion(e.getPlayer())) return;
		
		Player player = e.getPlayer();
		Player caught = (Player) e.getCaught();
		
		caught.teleport(player);
		
	}

}
