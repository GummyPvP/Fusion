package org.mcwarfare.cubecore.koth.gameplay;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.mcwarfare.cubecore.Cubecore;

/**
	 * 
	 * Created on Jul 11, 2016 by Jeremy Gooch.
	 * 
	 */

public class KOTHLoop implements Runnable {
	
	// This class has ideas from Avalon.
	
	private BukkitTask task;
	
	private KOTHLoop() { }
	
	private static KOTHLoop instance = new KOTHLoop();
	
	public static KOTHLoop get() {
		return instance;
	}

	@Override
	public void run() {
		for (Point point : Points.get().getPoints()) {
			
			//Bukkit.broadcastMessage(point.getName());
			point.tick();
				
		}
			
	}
		
	public void start() {
			
		this.task = Bukkit.getScheduler().runTaskTimer(Cubecore.getInstance(), this, 0, 20);
			
	}
		
	public void stop() {
			
		Bukkit.getScheduler().cancelTask(task.getTaskId());
			
	}
		
}