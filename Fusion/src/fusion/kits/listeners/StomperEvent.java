package fusion.kits.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fusion.kits.utils.KitManager;
import fusion.utils.chat.Chat;
import fusion.utils.protection.RegionManager;

/**
 * 
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class StomperEvent implements Listener {
	
	int maxDamage = 4;

	@EventHandler
	public void onStomp(EntityDamageEvent e) {
		
		if (!(e.getEntity() instanceof Player))
			return;
		if (e.getCause() != DamageCause.FALL)
			return;

		Player player = (Player) e.getEntity();

		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Stomper"))) return;
		
		if (RegionManager.getInstance().isInProtectedRegion(player)) {
			
			e.setCancelled(true);
			
			return;
		}
		
		int damage = e.getDamage();

		e.setCancelled(true);
		player.damage(damage > maxDamage ? maxDamage : damage);

		for (Entity entities : player.getNearbyEntities(5.0, 5.0, 5.0)) {

			if (!(entities instanceof Player))
				continue;

			Player target = (Player) entities;

			if (target.isSneaking()) target.damage(damage > maxDamage ? maxDamage : damage);
			else target.damage(damage);
			
			if (!target.isDead()) return;
			
			Chat.getInstance().messagePlayer(target, "You were squished by a stomper!");

		}

	}

}
