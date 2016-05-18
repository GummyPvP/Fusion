package fusion.kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.utils.Chat;
import fusion.utils.ItemBuilder;
import klap.utils.mPlayer;

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
	 * @return ItemStack list of armor. Make sure to put the armor in the right order when adding, helmet, chestplate, leggings, boots
	 */
	public abstract ItemStack[] getArmor();
	
	/**
	 * 
	 * @return Potion effects that should be applied.
	 */
	public abstract PotionEffect[] getPotionEffects();
	
	/**
	 * 
	 * @return if this kit is unlocked by purchasing in shop or is free by default.
	 */
	public abstract boolean isDefault();
	
	public void apply(Player player) {
		
		mPlayer user = mPlayer.getInstance(player);
		
		if (user.hasKit()) {
			
			player.sendMessage(String.format(Chat.BASE_COLOR + "You already have kit " + Chat.IMPORTANT_COLOR + "%s" + Chat.BASE_COLOR + "! Please type /clearkit to choose another kit.", user.getKit().getName()));
			
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
		
		player.getInventory().setArmorContents(getArmor());
		
		for (PotionEffect effect : getPotionEffects()) {
			
			if (effect == null) break;
			
			player.addPotionEffect(effect);
			
		}
		
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			
			player.getInventory().addItem(new ItemBuilder(Material.MUSHROOM_SOUP).name("&bSoup").lore("Drinking this soup heals you 3.5 hearts").build());
			
		}
		
		user.setKit(this);
		
	}

}
