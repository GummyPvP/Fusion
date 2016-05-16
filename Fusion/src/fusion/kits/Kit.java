package fusion.kits;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.ItemUtils;

/**
	 * 
	 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class Kit {
	
	public abstract String getName();
	
	public abstract List<ItemStack> getItems();
	
	public abstract List<ItemStack> getArmor();
	
	public abstract boolean isDefault();
	
	public void apply(Player player) {
		
		for (ItemStack items : getItems()) {
			
			player.getInventory().addItem(items);
			
		}
		
		for (ItemStack armor : getArmor()) {
			
			if (ItemUtils.isHelmet(armor)) {
				
				player.getInventory().setHelmet(armor);
				continue;
				
			}
			
			if (ItemUtils.isChestplate(armor)) {
				
				player.getInventory().setChestplate(armor);
				continue;
				
			}
			
			if (ItemUtils.areLeggings(armor)) {
				
				player.getInventory().setLeggings(armor);
				continue;
				
			}
			
			if (ItemUtils.areBoots(armor)) {
				
				player.getInventory().setBoots(armor);
				
			}
			
			break;
			
		}
		
	}

}
