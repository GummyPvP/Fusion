package fusion.kits;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.Chat;
import fusion.utils.ItemUtils;

/**
	 * 
	 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public abstract class Kit {
	
	
	/**
	 * 
	 * @return name of kit
	 */
	public abstract String getName();
	
	/**
	 * 
	 * @return ItemStack list of items
	 */
	public abstract List<ItemStack> getItems();
	
	/**
	 * 
	 * @return ItemStack list of armor. Make sure to put the armor in the right order when adding, helmet, chestplate, leggings, boots
	 */
	public abstract List<ItemStack> getArmor();
	
	/**
	 * 
	 * @return if this kit is unlocked by purchasing in shop or is free by default.
	 */
	public abstract boolean isDefault();
	
	public void apply(Player player) {
		
		Chat.getInstance().messagePlayer(player, "&aYou've recieved kit " + Chat.IMPORTANT_COLOR + getName() + ".");
		
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
