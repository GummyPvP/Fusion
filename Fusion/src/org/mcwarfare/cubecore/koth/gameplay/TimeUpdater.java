package org.mcwarfare.cubecore.koth.gameplay;

import java.util.Calendar;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.utils.Day;

/**
	 * 
	 * Created on Jul 14, 2016 by Jeremy Gooch.
	 * 
	 */

public class TimeUpdater implements Runnable {
	
	// class used to update time every minute (so when KOTH is ticking, it doesn't constantly have to check from the Calendar instance)
	
	private BukkitTask task;
	
	private int minutes = 1;
	
	private TimeUpdater() { }
	
	private static TimeUpdater instance = new TimeUpdater();
	
	public static TimeUpdater get() {
		return instance;
	}

	@Override
	public void run() {
		
		Cubecore.getInstance().setCalander(Calendar.getInstance(TimeZone.getTimeZone("EST")));
		Cubecore.getInstance().setToday(Day.getByInteger(Cubecore.getInstance().getCalendar().get(Calendar.DAY_OF_WEEK)));
		Cubecore.getInstance().setHour(Cubecore.getInstance().getCalendar().get(Calendar.HOUR_OF_DAY));
		Cubecore.getInstance().setMinutes(Cubecore.getInstance().getCalendar().get(Calendar.MINUTE));
		
	}
	
	public void start() {
		
		this.setTask(Bukkit.getScheduler().runTaskTimer(Cubecore.getInstance(), this, 0, (getMinutes() * 30) * 20));
		run();
		
	}

	public int getMinutes() {	
		return minutes;
	}

	public int setMinutes(int minutes) {
		this.minutes = minutes;
		return minutes;
	}

	public BukkitTask getTask() {
		return task;
	}

	public void setTask(BukkitTask task) {
		this.task = task;
	}

}
