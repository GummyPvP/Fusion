package fusion.utils.crates;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import fusion.utils.crates.Crate.CrateTier;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class CrateManager {
	
	private CrateManager() { }
	
	private static CrateManager instance = new CrateManager();

	public static CrateManager getInstance() {
		return instance;
	}
	
	private List<Crate> crates = new ArrayList<Crate>();
	
	public void registerCrate(Location location, CrateTier tier) {
		
		Crate crate = new Crate(location, tier);
		
		crates.add(crate);
		
	}
	
}
