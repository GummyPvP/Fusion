package fusion.events.utils;

import org.bukkit.Bukkit;

import fusion.utils.chat.Chat;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 4, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class Event {
	
	public abstract String getName();
	
	public abstract boolean areKitsEnabled();
	
	public abstract void register();
	
	public abstract Rank getRankRequired();
	
	public void start(String userStarting) {
		
		Bukkit.broadcastMessage(String.format(Chat.SECONDARY_BASE + "Event %s is being hosted by %s", getName(), userStarting));
		
	}

}
