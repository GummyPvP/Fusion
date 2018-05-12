package org.mcwarfare.cubecore.koth.gameplay.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.utils.region.Bounds;
import org.mcwarfare.cubecore.koth.utils.region.Region;
import org.mcwarfare.cubecore.koth.utils.regions.PointRegion;

/**
	 * 
	 * Created on Jul 13, 2016 by Jeremy Gooch.
	 * 
	 */

public class PointRegionManager {
	
	private PointRegionManager() { }
	
	private static PointRegionManager instance = new PointRegionManager();
	
	public static PointRegionManager get() {
		return instance;
	}
	
	private List<PointRegion> regions = new ArrayList<PointRegion>();
	
	public List<PointRegion> getRegions() {
		return regions;
	}
	
	public PointRegion getPointRegion(String name) {
		
		for (PointRegion region : regions) {
			
			if (region.getName().equalsIgnoreCase(name)) return region;
			
		}
		
		return null;
		
	}
	
	public void unregisterRegion(Region region) {
		
		this.regions.remove(region);
		region.unregister();
		
	}
	
	public void addRegion(PointRegion region) {
		
		this.regions.add(region);
		region.register();
		
	}
	
	public void deleteRegion(PointRegion region) {
		
		if (region == null) return;
		
		unregisterRegion(region);
		
		if (!Cubecore.getInstance().getRegionsFile().contains("regions.points." + region.getName())) return;
		
		Cubecore.getInstance().getRegionsFile().set("regions.points." + region.getName(), null);
		
	}
	
	public void registerRegion(String name) {
		
		World world = Bukkit.getWorld(Cubecore.getInstance().getRegionsFile().getString("regions.points." + name + ".world"));
		Vector min = Cubecore.getInstance().getRegionsFile().getVector("regions.points." + name + ".min");
		Vector max = Cubecore.getInstance().getRegionsFile().getVector("regions.points." + name + ".max");
		
		PointRegion region = new PointRegion(name, new Bounds(world, min, max));
		
		regions.add(region);
		region.register();
		
	}
	
	public void loadRegions() {
		
		if (Cubecore.getInstance().getRegionsFile().contains("regions.point") == false) return;
		
		for (String section : Cubecore.getInstance().getRegionsFile().getSection("regions.points").getKeys(false)) {
			
			registerRegion(section);
		
		}
		
	}
}
