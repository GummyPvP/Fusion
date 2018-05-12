package org.mcwarfare.cubecore.koth.gameplay.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.utils.region.Bounds;
import org.mcwarfare.cubecore.koth.utils.region.Region;
import org.mcwarfare.cubecore.koth.utils.regions.KOTHRegion;

/**
	 * 
	 * Created on Jul 12, 2016 by Jeremy Gooch.
	 * 
	 */

public class RegionManager {
	
	private RegionManager () {}
	
	private static RegionManager instance = new RegionManager();
	
	public static RegionManager get() {
		return instance;
	}
	
	private List<Region> regions = new ArrayList<Region>();
	
	public void registerRegion(Region region) {
		
		region.register();
		
		this.regions.add(region);
		
	}
	
	public List<Region> getRegions() {
		return regions;
	}
	
	public void loadRegion(String region) {
		
		if (!Cubecore.getInstance().getRegionsFile().contains("regions.koth." + region)) return;
		
		World world = Bukkit.getWorld(Cubecore.getInstance().getRegionsFile().getString("regions.koth." + region + ".world"));
		Vector min = Cubecore.getInstance().getRegionsFile().getVector("regions.koth." + region + ".min");
		Vector max = Cubecore.getInstance().getRegionsFile().getVector("regions.koth." + region + ".max");
		
		KOTHRegion kothRegion = new KOTHRegion(region, new Bounds(world, min, max));
		
		registerRegion(kothRegion);
		
	}
	
	public void loadRegions() {
		
		if (Cubecore.getInstance().getRegionsFile().getSection("regions.koth") == null) {
			
			System.out.println("There are no regions in the RegionFile!");
			
			return;
		}
		
		for (String section : Cubecore.getInstance().getRegionsFile().getSection("regions.koth").getKeys(false)) {
			
			loadRegion(section);
			
		}
		
	}
	public List<Region> getRegions(Vector vector) {
		
		List<Region> regionArray = new ArrayList<Region>();
		
		for (Region region : regions) {
			
			if (vector.isInAABB(region.getBounds().getMinimumPoint(), region.getBounds().getMaximumPoint())) regionArray.add(region);
			
		}
		
		return regionArray;
		
	}
	
	public void unregisterRegion(Region region) {
		
		region.unregister();
		regions.remove(region);
		
	}
	
	public void deleteRegion(Region region) {
		
		if (region == null) return;
		
		unregisterRegion(region);
		
		if (!Cubecore.getInstance().getRegionsFile().contains("regions." + region.getName())) return;
		
		Cubecore.getInstance().getRegionsFile().set("regions." + region.getName(), null);
		
	}
	
	public void saveRegions() {
		
		for (Region region : regions) {
			
			region.save();
			
		}
		
	}
	
	public Region getRegion(String regionName) {
		
		for (Region region : regions) {
			
			if (region.getName().equalsIgnoreCase(regionName)) return region;
			
		}
		
		return null;
		
	}
	
}
