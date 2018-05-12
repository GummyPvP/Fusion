package org.mcwarfare.cubecore.koth.utils.region;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * 
 * Created on Jul 10, 2016 by Jeremy Gooch.
 * 
 */

public class Bounds {

	private World world;
	private Vector minPoint, maxPoint;

	public Bounds(World world, Vector minPoint, Vector maxPoint) {
		
		this.world = world;
		this.minPoint = Vector.getMinimum(minPoint, maxPoint);
		this.maxPoint = Vector.getMaximum(minPoint, maxPoint);
		
	}
	
	public World getWorld() {
		return world;
	}
	
	public Vector getMinimumPoint() {
		return minPoint;
	}
	
	public Vector getMaximumPoint() {
		return maxPoint;
	}
	
	public boolean isInBounds(Location location) {
		
		return location.toVector().isInAABB(minPoint, maxPoint);
		
	}
	
	public boolean isInBounds(Vector vector) {
		
		return vector.isInAABB(minPoint, maxPoint);
		
	}
	
	public Vector getCenterPoint() {
		
		return (getMinimumPoint().clone().add(getMaximumPoint().clone())).multiply(0.5);
		
	}
	
	public List<Vector> getEdges() {
		
		int minX = minPoint.getBlockX();
		int maxX = maxPoint.getBlockX();
		
		int minY = minPoint.getBlockY();
		int maxY = maxPoint.getBlockY();
		
		int minZ = minPoint.getBlockZ();
		int maxZ = maxPoint.getBlockZ();
		
		int amountOfBlocks = (((maxX - minX) * (maxY - minY)) * 2) + (((maxZ - minZ) * (maxY - minY)) * 2);
		
		List<Vector> result = new ArrayList<Vector>(amountOfBlocks);
		
		for (int y = minY + 1; y <= maxY; y++) {
			
			for (int x = minX; x <= maxX; x++) {
				
				result.add(new Vector(x, y, minZ));
				result.add(new Vector(x, y, maxZ));
				
			}
			
			for (int z = minZ; z <= maxZ; z++) {
				
				result.add(new Vector(minX, y, z));
				result.add(new Vector(maxX, y, z));
				
			}
			
		}
		
		return result;
		
	}
	
}
