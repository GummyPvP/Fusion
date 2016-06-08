package fusion.events.utils;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 4, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Countdown extends BukkitRunnable {
	
	public int time;
	public String message, id;
	
	static Set<Countdown> running = new HashSet<Countdown>();
	
	public Countdown(String id, int count, String message) {
		
		this.time = count;
		this.message = message;
		this.id = id;
		
		running.add(this);
		
	}
	
	public String getID() {
		return id;
	}
	
	public int getTime() {
		return time;
	}
	
	public String getMessage() {
		return message;
	}
	
	public static Countdown getInstance(String instance) {
		
		for (Countdown instances : running) {
			
			if (instances.getID().equalsIgnoreCase(instance)) return instances;
			
		}
		
		return null;
		
	}

	@Override
	public void run() {
		
		if (time == 0) {
			
			cancel();
			
			running.remove(this);
			
			return;
		}
		
		Bukkit.broadcastMessage(String.format(message, time));
		
		time--;
		
	}

}
