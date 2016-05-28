package fusion.kits.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.utils.Chat;
import fusion.utils.ItemBuilder;
import fusion.utils.mKitUser;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.ProtectedRegion.HealingItem;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;

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
	 * @return item for KitGUI
	 */
	public abstract ItemStack getInventoryItem();
	
	/**
	 * 
	 * @return ItemStack list of items
	 */
	public abstract List<ItemStack> getItems();
	
	/**
	 * 
	 * @return ItemStack list of armor. Make sure to put the armor in the inverse order when adding, boots, leggings, chestplate, helmet
	 */
	public abstract ItemStack[] getArmor();
	
	/**
	 * 
	 * @return Potion effects that should be applied.
	 */
	public abstract PotionEffect[] getPotionEffects();
	
	public abstract String getSpecialAdvantageString();
	
	/**
	 * 
	 * @return if this kit is unlocked by purchasing in shop or is free by default.
	 */
	public abstract boolean isDefault();
	
	public abstract double getCost();
	
	public void apply(Player player) {
		
		mKitUser user = mKitUser.getInstance(player);
		
		if (user.hasKit()) {
			
			Chat.getInstance().messagePlayer(player, String.format(Chat.BASE_COLOR + "You already have kit " + Chat.IMPORTANT_COLOR + "%s" + Chat.BASE_COLOR + "! Please type /clearkit to choose another kit.", user.getKit().getName()));
			
			return;
		}
		
		if (!user.ownsKit(this) && !isDefault()) {
			
			Chat.getInstance().messagePlayer(player, String.format(Chat.BASE_COLOR + "You do not own " + Chat.IMPORTANT_COLOR + "%s", getName()));
			
			return;
		}
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		for (PotionEffect effects : player.getActivePotionEffects()) {
			
			player.removePotionEffect(effects.getType());
			
		}
		
		Chat.getInstance().messagePlayer(player, "&aYou've recieved kit " + Chat.IMPORTANT_COLOR + getName() + ".");
		
		for (ItemStack items : getItems()) {
			
			player.getInventory().addItem(items);
			
		}
		
		Collections.reverse(Arrays.asList(getArmor()));
		
		player.getInventory().setArmorContents(getArmor());
		
		if (getPotionEffects() != null) {
			
			for (PotionEffect effect : getPotionEffects()) {
				
				if (effect == null) break;
				
				player.addPotionEffect(effect);
				
			}
			
		}
		
		if (RegionManager.getInstance().getRegion(player.getLocation().toVector()) != null) {
			
			Region region = RegionManager.getInstance().getRegion(player.getLocation().toVector());
			
			if (region instanceof ProtectedRegion) {
				
				ProtectedRegion protectedRegion = (ProtectedRegion) region;
				
				if (protectedRegion.getHealingItem() != HealingItem.ANY) {
					
					for (int i = 0; i < (protectedRegion.areRefillsAllowed() ? player.getInventory().getSize() : 8); i++) {
						
						player.getInventory().addItem(protectedRegion.getHealingItem().getItem());
						
					}
					
					return;
					
				}
				
			}
			
		}
		
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			
			player.getInventory().addItem(new ItemBuilder(Material.MUSHROOM_SOUP).name("&bSoup").lore("Drinking this soup heals you 3.5 hearts").build());
			
		}
		
		user.setKit(this);
		user.getPlayer().closeInventory();
		
	}

}
