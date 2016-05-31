package fusion.utils.protection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
	 * 
	 * Copyright GummyPvP. Created on May 27, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Bounds {
	
	World world;
	Vector min, max;
	
	public Bounds(World world, Vector min, Vector max) {
		
		this.world = world;
		this.min = Vector.getMinimum(min, max);
		this.max = Vector.getMaximum(min, max);
		
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Vector getMin() {
		return min;
	}

	public void setMin(Vector min) {
		this.min = min;
	}

	public Vector getMax() {
		return max;
	}

	public void setMax(Vector max) {
		this.max = max;
	}
	
	public boolean inBounds(Location location) {
		
		return location.toVector().isInAABB(min, max);
		
	}
	
	public boolean inBounds(Vector vector) {
		
		return vector.isInAABB(min, max);
		
	}
	
	public boolean isInRegion(Region region) {
		
		Bounds thisRegion = this;
		Bounds otherRegion = region.getBounds();
		
		return (otherRegion.inBounds(thisRegion.getCenter()));
		
	}
	
	public Vector getCenter() {
		
		return (min.clone().add(max.clone())).multiply(0.5);
		
	}

}
