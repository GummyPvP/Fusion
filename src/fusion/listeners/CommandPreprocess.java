package fusion.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fusion.kits.utils.KitManager;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 16, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class CommandPreprocess implements Listener {
	
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
		
		Player player = e.getPlayer();
		
		if (e.getMessage().startsWith("/") && KitManager.getInstance().valueOf(e.getMessage().substring(0, 1)) != null) {
			
			KitManager.getInstance().valueOf(e.getMessage().substring(0, 1)).apply(player);
			
		}
		
	}

}
