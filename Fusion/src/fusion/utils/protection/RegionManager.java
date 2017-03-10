package fusion.utils.protection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
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
		
		if (!Fusion.getInstance().getRegionsFile().contains("regions." + name)) return;
		
		Vector min, max;
		World world;
		
		boolean pvpEnabled, refills;
		HealingItem item;
		
		min = Fusion.getInstance().getRegionsFile().getVector("regions." + name + ".minPoint");
		max = Fusion.getInstance().getRegionsFile().getVector("regions." + name + ".maxPoint");
		world = Bukkit.getWorld(Fusion.getInstance().getRegionsFile().getString("regions." + name + ".world"));
		
		pvpEnabled = Fusion.getInstance().getRegionsFile().getBoolean("regions." + name + ".pvpEnabled");
		refills = Fusion.getInstance().getRegionsFile().getBoolean("regions." + name + ".refills");
		item = HealingItem.valueOf(Fusion.getInstance().getRegionsFile().getString("regions." + name + ".healthItem"));
		
		ProtectedRegion region = new ProtectedRegion(name, world, min, max);
		
		if (Fusion.getInstance().getRegionsFile().getStringList("regions." + name + ".refills") != null) {
			
			for (String kitName : Fusion.getInstance().getRegionsFile().getStringList("regions." + name + ".blockedKits")) {
				
				region.addBlockedKit(KitManager.getInstance().valueOf(kitName));
				
			}
			
		}
		
		region.setHealthItem(item);
		region.togglePVP(pvpEnabled);
		region.toggleRefills(refills);
		
		region.register();
		registerRegion(region);
		
	}
	
	public void loadRegions() {
		
		if (!Fusion.getInstance().getRegionsFile().contains("regions")) return;
		
		for (String section : Fusion.getInstance().getRegionsFile().getSection("regions").getKeys(false)) {
			
			loadRegion(section);
			
		}
		
	}
	
	public List<Region> getRegions(Vector vector) {
		
		List<Region> regionArray = new ArrayList<Region>();
		
		for (Region region : regions) {
			
			if (vector.isInAABB(region.getBounds().getMin(), region.getBounds().getMax())) regionArray.add(region);
			
		}
		
		return regionArray;
		
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
	
	public Region getSmallestRegion(List<Region> regions) {
		
		Region smallest = null;
		
		if (regions.isEmpty()) return null;
		
		for (Region region : regions) {
			
			// default check just in case there is only one region
			
			if (smallest == null) {
				
				smallest = region;
				continue;
			}
			
			// checks if region is smaller than "smallest"
			
			if (region.getBounds().getVolume() < smallest.getBounds().getVolume()) {
				
				smallest = region;
				continue;
				
			}
			
		}
		
		return smallest;
		
	}
	
	public boolean isInProtectedRegion(Player player) {

		Region region = getSmallestRegion(getRegions(player.getLocation().toVector()));

		if (region == null) return false;

		return region instanceof ProtectedRegion && !((ProtectedRegion) region).isPVPEnabled();

	}
	
	public void removeRegion(Region region) {
		
		regions.remove(region);
		
		if (region instanceof ProtectedRegion) {
			
			((ProtectedRegion) region).unregister();
			
		}
		
		if (!Fusion.getInstance().getRegionsFile().contains("regions." + region.getName())) return;
		
		Fusion.getInstance().getRegionsFile().set("regions." + region.getName(), null);
	}

}
