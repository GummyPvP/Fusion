package fusion.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class EntityDamageByEntity implements Listener {
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
		
		Entity entityReciever = e.getEntity();
		
		Entity entityHitter = e.getDamager();
		
		if (!(entityHitter instanceof Player)) return;
		
		Player hitter = (Player) entityHitter;
		
		if (hitter.getItemInHand() == null) return;
		
		if (hitter.getItemInHand().getType().toString().contains("SWORD") || hitter.getItemInHand().getType().toString().contains("AXE")) {
			
			hitter.getItemInHand().setDurability((short) -1);
			
		}
		
		if (!(entityReciever instanceof Player)) return;
		
		Player reciever = (Player) entityReciever;
		
		for (ItemStack armor : reciever.getInventory().getArmorContents()) {
			
			armor.setDurability((short) -1);
			
		}
		
	}

}
