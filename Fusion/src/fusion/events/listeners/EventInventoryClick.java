package fusion.events.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fusion.events.Event;
import fusion.events.EventManager;
import fusion.utils.chat.Chat;
import fusion.utils.gui.EventJoinGUI;

/**
	 * 
	 * Created on Dec 30, 2016 by Jeremy Gooch.
	 * 
	 */

public class EventInventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		Player player = (Player) e.getWhoClicked();
		
		if (e.getInventory().getName().contains(EventJoinGUI.get().getInventory().getName())) {
			
			if (e.getCurrentItem() == null) return;
			if (!e.getCurrentItem().hasItemMeta()) return;
			if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;
			
			ItemStack item = e.getCurrentItem().clone();
			
			e.setCancelled(true);
			
			player.closeInventory();
			
			if (item.getItemMeta().getDisplayName().contains("#1")) {
				
				Event event = EventManager.get().getActiveEvents().get(0);
				
				if (event.getPlayers().contains(player.getName())) {
					
					Chat.getInstance().messagePlayer(player, "You are already in this event!");
					
					return;
				}
				
				event.addPlayer(player);
				
				return;
			}
			
			if (item.getItemMeta().getDisplayName().contains("#2")) {
				
				Event event = EventManager.get().getActiveEvents().get(1);
				
				if (event.getPlayers().contains(player.getName())) {
					
					Chat.getInstance().messagePlayer(player, "You are already in this event!");
					
					return;
				}
				
				event.addPlayer(player);
				
				return;
			}
			
		}
		
	}

}
