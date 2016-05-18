package fusion.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fusion.kits.KitManager;
import fusion.utils.KitGUI;

/**
	 * 
	 * Copyright GummyPvP. Created on May 17, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (!e.getInventory().getName().equalsIgnoreCase(KitGUI.INVENTORY_NAME)) return;
		if (!e.getCurrentItem().hasItemMeta()) return;
		
		Player player = (Player) e.getWhoClicked();
		
		e.setCancelled(true);
		
		if (KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) == null) return; // do different items in the future
		
		KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())).apply(player);
		
	}

}
