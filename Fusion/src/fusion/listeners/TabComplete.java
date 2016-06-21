package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

/**
	 * 
	 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class TabComplete implements Listener {
	
	private String[] blockedCommands = new String[] { "version", "ver", "about", "icanhasbukkit" };
	
	@EventHandler
	public void onTabComplete(PlayerChatTabCompleteEvent e) {
		
		for (String blocked : blockedCommands) {
			
			if (e.getChatMessage().toLowerCase().startsWith("/" + blocked) || e.getChatMessage().equalsIgnoreCase("/" + blocked)) e.getTabCompletions().clear();
			
		}
	}

}
