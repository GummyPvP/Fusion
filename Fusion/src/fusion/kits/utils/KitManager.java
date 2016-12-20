package fusion.kits.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fusion.utils.mKitUser;

/**
	 * 
	 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class KitManager {
	
	private KitManager() { }
	
	private static KitManager instance = new KitManager();
	
	public static KitManager getInstance() {
		
		return instance;
		
	}
	
	private List<Kit> kits = new ArrayList<Kit>();
	
	public void registerKit(Kit kit) {
		
		kits.add(kit);
		
	}
	
	public void unloadKits() {
		
		kits.clear();
		
	}
	
	public List<Kit> getKits() {
		
		return kits;
		
	}
	
	public Kit valueOf(String name) {
		
		for (Kit allKits : kits) {
			
			if (allKits.getName().equalsIgnoreCase(name)) return allKits;
			
		}
		
		return null;
		
	}
	
	public Kit valueOf(Kit kit) {
		
		for (Kit allKits : kits) {
			
			if (kit.getName() == allKits.getName()) return allKits;
			
		}
		
		return null;
		
	}
	
	public boolean hasRequiredKit(Player player, Kit required) {
		
		if (!mKitUser.getInstance(player).hasKit()) return false;
		
		if (mKitUser.getInstance(player).getKit().getName() != required.getName()) return false;
		
		return true;
		
	}
	
	public boolean hasRequiredKit(Player player, String required) {
		
		if (!mKitUser.getInstance(player).hasKit()) return false;
		
		if (valueOf(required) == null) return false;
		
		if (mKitUser.getInstance(player).getKit().getName() != valueOf(required).getName()) return false;
		
		return true;
		
	}
}
