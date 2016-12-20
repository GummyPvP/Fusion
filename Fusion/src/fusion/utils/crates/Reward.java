package fusion.utils.crates;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class Reward {
	
	public abstract String getName();
	
	public abstract ItemStack getItem();
	
	public abstract void apply(Player player);
	
}
