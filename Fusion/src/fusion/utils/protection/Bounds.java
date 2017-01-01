package fusion.utils.protection;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

/**
	 * 
	 * Copyright GummyPvP. Created on May 27, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Bounds implements ConfigurationSerializable {
	
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
	
	public int getVolume() {
		
		int minX = min.getBlockX();
		int maxX = max.getBlockX();
		
		int minY = min.getBlockY();
		int maxY = max.getBlockY();
		
		int minZ = min.getBlockZ();
		int maxZ = max.getBlockZ();
		
		int length = Math.abs(maxX - minX);
		int width = Math.abs(maxZ - minZ);
		int height = Math.abs(maxY - minY);
		
		int volume = length * width * height;
		
		return volume;
		
	}

	@Override
	public Map<String, Object> serialize() {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("world", world.getName());
		
		data.put("minVector", min.serialize());
		data.put("maxVector", max.serialize());
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public static Bounds deserialize(Map<String, Object> data) {
		
		World world = Bukkit.getWorld((String) data.get("world"));
		
		Vector minVector = Vector.deserialize((Map<String, Object>) data.get("minVector"));
		Vector maxVector = Vector.deserialize((Map<String, Object>) data.get("maxVector"));
		
		return new Bounds(world, minVector, maxVector);
	}

}
