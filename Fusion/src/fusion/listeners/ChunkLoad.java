package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import fusion.main.Main;
import fusion.utils.CandyMan;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 3, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class ChunkLoad implements Listener {
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		
		Main.registerEntity(CandyMan.class, "Candyman", 120);
		
	}

}
