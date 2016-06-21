package fusion.utils.warps;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Warp {
	
	String name;
	Location location;
	ItemStack inventoryItem;
	
	public Warp(String name, Location location, ItemStack inventoryItem) {
		
		this.name = name;
		this.location = location;
		this.inventoryItem = inventoryItem;
		
	}
	
	public Location getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack getInventoryItem() {
		return inventoryItem;
	}

}
