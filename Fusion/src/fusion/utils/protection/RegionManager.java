package fusion.utils.protection;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.Vector;

import fusion.utils.ConfigManager;
import fusion.utils.protection.ProtectedRegion.HealingItem;

/**
	 * 
	 * Copyright GummyPvP. Created on May 27, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class RegionManager {
	
	private RegionManager() { }
	
	private static RegionManager instance = new RegionManager();
	
	public static RegionManager getInstance() {
		return instance;
	}
	
	private Set<Region> regions = new HashSet<Region>();
	
	public void registerRegion(Region region) {
		
		regions.add(region);
		
	}
	
	public Set<Region> getRegions() {
		
		return regions;
		
	}
	
	public void saveRegions() {
		
		if (regions.isEmpty()) return;
		
		for (Region region : regions) {
			
			region.save();
			
		}
		
	}
	
	private void loadRegion(String name) {
		
		if (!ConfigManager.getRegionsFile().contains("regions." + name)) return;
		
		Vector min, max;
		World world;
		
		boolean pvpEnabled, refills;
		HealingItem item;
		
		min = ConfigManager.getRegionsFile().getVector("regions." + name + ".minPoint");
		max = ConfigManager.getRegionsFile().getVector("regions." + name + ".maxPoint");
		world = Bukkit.getWorld(ConfigManager.getRegionsFile().getString("regions." + name + ".world"));
		
		pvpEnabled = ConfigManager.getRegionsFile().getBoolean("regions." + name + ".pvpEnabled");
		refills = ConfigManager.getRegionsFile().getBoolean("regions." + name + ".refills");
		item = HealingItem.valueOf(ConfigManager.getRegionsFile().getString("regions." + name + ".healthItem"));
		
		ProtectedRegion region = new ProtectedRegion(name, world, min, max);
		
		region.setHealthItem(item);
		region.togglePVP(pvpEnabled);
		region.toggleRefills(refills);
		
		registerRegion(region);
		
	}
	
	public void loadRegions() {
		
		if (!ConfigManager.getRegionsFile().contains("regions")) return;
		
		for (String section : ConfigManager.getRegionsFile().getSection("regions").getKeys(false)) {
			
			loadRegion(section);
			
		}
		
	}
	
	public Region getRegion(Vector vector) {
		
		for (Region region : regions) {
			
			if (vector.isInAABB(region.getBounds().getMin(), region.getBounds().getMax())) return region;
			
		}
		
		return null;
		
	}
	
	public Region getRegion(String name) {
		
		for (Region region : regions) {
			
			if (region.getName().equalsIgnoreCase(name)) return region;
			
		}
		
		return null;
		
	}

	public boolean regionExists(String regionName) {
		
		for (Region region : regions) {
			
			if (regionName.equalsIgnoreCase(region.getName())) return true;
			
		}
		
		return false;
		
	}

}
