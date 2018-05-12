package org.mcwarfare.cubecore.koth.utils.region;

	/**
	 * 
	 * Created on Jul 10, 2016 by Jeremy Gooch.
	 * 
	 */

public abstract class Region {
	
	public abstract String getName();
	
	public abstract Bounds getBounds();
	
	public abstract void save();
	
	public abstract void register();
	
	public abstract void unregister();

}
