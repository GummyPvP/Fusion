package org.mcwarfare.cubecore;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcwarfare.cubecore.koth.cmds.CreatePoint;
import org.mcwarfare.cubecore.koth.cmds.CreateRegion;
import org.mcwarfare.cubecore.koth.cmds.DeletePoint;
import org.mcwarfare.cubecore.koth.cmds.DeleteRegion;
import org.mcwarfare.cubecore.koth.cmds.EditPoint;
import org.mcwarfare.cubecore.koth.cmds.ForceEnd;
import org.mcwarfare.cubecore.koth.cmds.ForceStart;
import org.mcwarfare.cubecore.koth.cmds.Koth;
import org.mcwarfare.cubecore.koth.cmds.Regions;
import org.mcwarfare.cubecore.koth.gameplay.Points;
import org.mcwarfare.cubecore.koth.gameplay.TimeUpdater;
import org.mcwarfare.cubecore.koth.gameplay.managers.PointRegionManager;
import org.mcwarfare.cubecore.koth.gameplay.managers.RegionManager;
import org.mcwarfare.cubecore.koth.utils.Day;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fusion.main.Fusion;
import fusion.utils.ConfigManager;

public class Cubecore {

	 
	private static Cubecore mccubecore;
	private ConfigManager messages, regions, points, config;
	private Day today;
	private int hour, minutes;
	private Calendar calendar;

	private int defaultCaptureTime = 10; // minutes
	
	
	public void onEnable() {
		
		mccubecore = this;
		
		this.calendar = Calendar.getInstance();

		this.today = Day.getByInteger(calendar.get(Calendar.DAY_OF_WEEK));
		messages = new ConfigManager("messages", null);
		regions = new ConfigManager("regions", null);
		points = new ConfigManager("points", null);
		config = new ConfigManager("config", null);

		loadCommands(new CreatePoint(), new CreateRegion(), new ForceStart(),
				new Regions(), new DeleteRegion(), new DeletePoint(), new Koth(), new ForceEnd(), new EditPoint());

		System.out.println(ChatColor.RED + "Commands have been registered!");
		
		startKOTH();

		defaultCaptureTime = getConfiguration().getInt("settings.koth.defaultCaptureTime");

		if (Bukkit.getPluginManager().getPlugin("WorldEdit") == null) {

			System.out.println(
					"This server does not have WorldEdit installed, selections will not be able to be made. Previously saved regions will still work, but you cannot create new ones.");

		}
	}

	private void startKOTH() {

		RegionManager.get().loadRegions();

		PointRegionManager.get().loadRegions();

		Points.get().loadPoints();

		Points.get().start();

		TimeUpdater.get().start();

	}

	public void onDisable() {

		Points.get().savePoints();
		RegionManager.get().saveRegions();
		mccubecore = null;

	}

	private void loadListeners(Listener... listeners) {

		for (Listener l : listeners) {

			Bukkit.getPluginManager().registerEvents(l, Fusion.getInstance());

		}

	}
	
	public static Cubecore getInstance() {

		return mccubecore;

	}

	public ConfigManager getMessagesConfiguration() {

		return messages;

	}

	public ConfigManager getRegionsFile() {
		return regions;
	}

	public ConfigManager getPointsFile() {
		return points;
	}
	
	public Day getToday() {
		return today;
	}

	public void setToday(Day day) {
		this.today = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setCalander(Calendar c) {
		this.calendar = c;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public ConfigManager getConfiguration() {
		return config;
	}

	public int getDefaultCaptureTime() {
		return defaultCaptureTime;
	}

	public WorldEditPlugin getWorldEdit() {

		if (Bukkit.getPluginManager().getPlugin("WorldEdit") == null) {

			System.out.println(
					"This server does not have WorldEdit installed, selections will not be able to be made. Previously saved regions will still work, but you cannot create new ones.");

			return null;
		}

		return (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");

	}

}
