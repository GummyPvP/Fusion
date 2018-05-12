package org.mcwarfare.cubecore.koth.utils.regions;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.gameplay.RegionTracker;
import org.mcwarfare.cubecore.koth.utils.region.Bounds;
import org.mcwarfare.cubecore.koth.utils.region.Region;

/**
	 * 
	 * Created on Jul 10, 2016 by Jeremy Gooch.
	 * 
	 */

public class KOTHRegion extends Region {
	
	private String name;
	private Bounds bounds;
	
	private final RegionTracker tracker;
	
	public KOTHRegion(String name, Bounds bounds) {
		
		this.name = name;
		this.bounds = bounds;
		
		this.tracker = new RegionTracker(this);
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Bounds getBounds() {
		return bounds;
	}

	@Override
	public void save() {
		
		Cubecore.getInstance().getRegionsFile().set("regions.koth." + name + ".world", bounds.getWorld().getName());
		Cubecore.getInstance().getRegionsFile().set("regions.koth." + name + ".min", bounds.getMinimumPoint());
		Cubecore.getInstance().getRegionsFile().set("regions.koth." + name + ".max", bounds.getMaximumPoint());
		
	}

	@Override
	public void register() {
		
		Bukkit.getPluginManager().registerEvents(tracker, Cubecore.getInstance());
		
	}

	@Override
	public void unregister() {
		
		HandlerList.unregisterAll(tracker);
		
	}

	public RegionTracker getTracker() {
		return tracker;
	}

}
