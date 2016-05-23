package fusion.listeners;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fusion.utils.ItemBuilder;

/**
	 * 
	 * Copyright GummyPvP. Created on May 20, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		
		if (item == null) return;
		
		if (item.getType() != Material.MUSHROOM_SOUP) return;
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			
			e.setCancelled(true);
		
			player.setHealth(player.getHealth() + 7.0 >= player.getMaxHealth() ? player.getMaxHealth() : player.getHealth() + 7.0);
			
			ItemStack bowl = new ItemBuilder(Material.BOWL).name("&bBowl").lore(Arrays.asList("A bowl is most useful when it's empty.", "Hah! Just kidding! You're gonna die.")).build();
			
			player.setItemInHand(bowl);
			
			player.playSound(player.getLocation(), Sound.BURP, 1, 1);
			
			player.updateInventory();
		}
	}
}
