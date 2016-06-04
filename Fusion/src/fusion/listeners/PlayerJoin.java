package fusion.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.spawn.Spawn;

/**
	 * 
	 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		final Player player = e.getPlayer();
		
		mKitUser.getInstance(player).load();
		
		Utils.giveDefaultItems(player);
		
		Spawn.getInstance().teleport(player);
		
	}

}
