package fusion.utils.crates;

import java.util.ArrayList;
import java.util.List;

import fusion.utils.crates.crates.Common;
import fusion.utils.crates.crates.Unusual;

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
	
	public void registerCrate(Crate crate) {
		
		crates.add(crate);
		
	}
	
	public Crate getCrate(String name) {
		
		for (Crate allCrates : crates) {
			
			if (allCrates.getName().equalsIgnoreCase(name)) return allCrates;
			
		}
		
		return null;
		
	}
	
	public void loadCrates() {
		
		registerCrate(new Common());
		registerCrate(new Unusual());
		
	}
	
}
