package fusion.utils.editing.regions;

import fusion.utils.editing.Bounds;

/**
	 * 
	 * Copyright GummyPvP. Created on May 27, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class Region {
	
	public abstract String getName();
	
	public abstract void save();
	
	public abstract Bounds getBounds();

}
