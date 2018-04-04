package fusion.kits.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import fusion.kits.utils.KitManager;
import fusion.utils.mKitUser;

/**
	 * 
	 * Created on Dec 6, 2016 by Jeremy Gooch.
	 * 
	 */

public class TurtleEvent implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		
		if (!(e.getEntity() instanceof Player)) return;
		
		Player player = (Player) e.getEntity();
		
		if (!KitManager.getInstance().hasRequiredKit(player, "Turtle")) return;
		
		if (mKitUser.getInstance(player).isInGladiatorArena())
			return;
		
		if (!player.isSneaking()) return;
		
		switch (e.getCause()) {
		
		case FALL:
		case LAVA:
			return;
			
		default:
			
			double damage = e.getDamage();
			
			damage *= .25;
			
			e.setCancelled(true);
			
			player.damage(damage);
			
			break;
		}
		
	}
	
//	@EventHandler
//	public void onPlayerDamage(EntityDamageByEntityEvent e) {
//		
//		if (!(e.getEntity() instanceof Player)) return;
//		if (!(e.getDamager() instanceof Player)) return;
//		
//		Player damager = (Player) e.getDamager();
//		
//		if (!KitManager.getInstance().hasRequiredKit(damager, "Turtle")) return;
//			
//		if (!damager.isSneaking()) return;
//		
//		e.setCancelled(true);
//	}

}
