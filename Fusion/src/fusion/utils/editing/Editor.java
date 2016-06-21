package fusion.utils.editing;

import org.bukkit.Material;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class Editor {
	
	public abstract String getName();
	
	public abstract Material getTool();
	
	public abstract Editor clone();

}
