package fusion.utils.protection;

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
