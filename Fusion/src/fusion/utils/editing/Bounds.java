package fusion.utils.editing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

import fusion.utils.editing.regions.Region;

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
	
	public List<Vector> getEdges() {
		
		int minX = min.getBlockX();
		int maxX = max.getBlockX();
		
		int minY = min.getBlockY();
		int maxY = max.getBlockY();
		
		int minZ = min.getBlockZ();
		int maxZ = max.getBlockZ();
		
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

	@SuppressWarnings("deprecation")
	public void generateHollowCube(int radius) { // Thanks Bukkit forums
		
		final byte floorData = (byte) new Random().nextInt(16);
		final byte wallData = (byte) new Random().nextInt(16);
		
		for (int x = 0; x < radius; x++) {
		    for (int z = 0; z < radius; z++) {
		        for (int y = 0; y <= radius / 2; y++) {
		            Location loc = new Location(world, min.getBlockX() + x, min.getBlockY() + y, min.getBlockZ() + z);
		            Block block = loc.getBlock();
		            if (y != (radius / 2) && y!=0) {
		                if ((x >= 0 && z == 0) || (x >= 0 && z == radius - 1) ||( x == 0 && z >= 0) || (x == radius - 1 && z >= 0)) { // walls
		                	block.setType(Material.STAINED_GLASS);
		                	block.setData(wallData);
		                }
		            } else { // floors
		                block.setType(Material.STAINED_GLASS);
		                block.setData(floorData);
		            }
		        }
		    }
		}
		
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
