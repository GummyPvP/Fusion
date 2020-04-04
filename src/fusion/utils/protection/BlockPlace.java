package fusion.utils.protection;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * 
 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {

		Player player = e.getPlayer();

		if (player.getGameMode() == GameMode.CREATIVE)
			return;

		e.setCancelled(true);

	}

}
