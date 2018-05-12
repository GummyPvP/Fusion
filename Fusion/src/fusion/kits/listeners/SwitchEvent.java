package fusion.kits.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fusion.kits.utils.KitManager;
import fusion.utils.mKitUser;
import fusion.utils.editing.regions.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 5, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class SwitchEvent implements Listener {
	
	@EventHandler
	public void onSwitch(EntityDamageByEntityEvent e) {
		
		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getDamager() instanceof Snowball)) return;
		
		if (!(((Snowball) e.getDamager()).getShooter() instanceof Player)) return;
		
		Player shooter = (Player) ((Snowball) e.getDamager()).getShooter();
		
		if (mKitUser.getInstance(shooter).getKit() != KitManager.getInstance().valueOf("Switcher")) return;
		if (mKitUser.getInstance(shooter).isInGladiatorArena()) return;
		if (RegionManager.getInstance().isInProtectedRegion(shooter)) return;
		if (RegionManager.getInstance().isInProtectedRegion((Player) e.getEntity())) return;
		
		Location shooterLocation, recieverLocation;
		
		shooterLocation = shooter.getLocation();
		recieverLocation = e.getEntity().getLocation();
		
		shooter.teleport(recieverLocation);
		e.getEntity().teleport(shooterLocation);
		
	}

}
