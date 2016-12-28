package fusion.events.utils;

import java.util.Set;

import org.bukkit.Location;

import fusion.utils.protection.Bounds;

/**
	 * 
	 * Created on Dec 23, 2016 by Jeremy Gooch.
	 * 
	 */

public abstract class Arena {
	
	public abstract String getName();
	
	public abstract Bounds getBounds();
	
	public abstract Location getMainSpawn();
	
	public abstract Set<Location> getAlternateSpawns();

}
