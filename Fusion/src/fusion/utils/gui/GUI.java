package fusion.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class GUI {
	
	public Inventory inv;
	
	public GUI(int size, String title) {
		
		this.inv = Bukkit.createInventory(null, size, title);
		
		populateInventory();
		
	}
	
	public GUI(int size, String title, Player player) {
		
		this.inv = Bukkit.createInventory(player, size, title);
		
		populateInventory();
		
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	public abstract void populateInventory();

}
