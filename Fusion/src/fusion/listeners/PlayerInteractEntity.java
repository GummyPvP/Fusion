package fusion.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import fusion.utils.gui.CandyGUI;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 4, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerInteractEntity implements Listener {
	
	@EventHandler
	public void onPlayerEntityInteract(PlayerInteractEntityEvent e) {
		
		Player player = e.getPlayer();
		Entity clicked = e.getRightClicked();
		
		if (clicked.getType() != EntityType.VILLAGER) return;
		
		Villager villager = (Villager) clicked;
		
		if (villager.getCustomName() == null) return;
		if (!villager.getCustomName().contains("Candy Man")) return;
		
		e.setCancelled(true);
		
		new CandyGUI(player);
		
	}

}
