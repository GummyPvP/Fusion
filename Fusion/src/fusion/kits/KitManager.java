package fusion.kits;

import java.util.HashSet;
import java.util.Set;

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
	
	private Set<Kit> kits = new HashSet<Kit>();
	
	public void registerKit(Kit kit) {
		
		kits.add(kit);
		
	}
	
	public void unloadKits() {
		
		kits.clear();
		
	}
	
	public Kit valueOf(String name) {
		
		for (Kit allKits : kits) {
			
			if (allKits.getName().equalsIgnoreCase(name)) return allKits;
			
		}
		
		return null;
		
	}
	
	public void setKit(mKitUser user, Kit kit) {
		
		user.setKit(kit);
		
	}

}
