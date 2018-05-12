package org.mcwarfare.cubecore.koth.utils.regions;

import org.bukkit.event.HandlerList;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.gameplay.PointTracker;
import org.mcwarfare.cubecore.koth.utils.region.Bounds;
import org.mcwarfare.cubecore.koth.utils.region.Region;

/**
	 * 
	 * Created on Jul 11, 2016 by Jeremy Gooch.
	 * 
	 */

public class PointRegion extends Region {
	
	private String name;
	private Bounds bounds;
	
	final PointTracker tracker;
	
	public PointRegion(String name, Bounds bounds) {
		
		this.name = name;
		this.bounds = bounds;
		
		this.tracker = new PointTracker(this);
		
	}
	
	public PointTracker getTracker() {
		return tracker;
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
		
		Cubecore.getInstance().getRegionsFile().set("regions.points." + name + ".world", bounds.getWorld().getName());
		Cubecore.getInstance().getRegionsFile().set("regions.points." + name + ".min", bounds.getMinimumPoint());
		Cubecore.getInstance().getRegionsFile().set("regions.points." + name + ".max", bounds.getMaximumPoint());
		
	}
	
	@Override
	public void register() {
		
		Cubecore.getInstance().getServer().getPluginManager().registerEvents(tracker, Cubecore.getInstance());
		
	}

	@Override
	public void unregister() {
		
		HandlerList.unregisterAll(tracker);
		
	}
	
	

}
