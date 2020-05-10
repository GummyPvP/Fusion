package fusion.kits.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fusion.kits.utils.KitManager;
import fusion.utils.editing.regions.RegionManager;

public class AnchorEvent implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		
		if (!(event.getEntity() instanceof Player)) return;
		
		if (!(event.getDamager() instanceof Player)) return;
		
		Player damaged = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();
		
		if (RegionManager.getInstance().isInProtectedRegion(damaged))
			return;
		
		if (RegionManager.getInstance().isInProtectedRegion(damager))
			return;
		
		if (KitManager.getInstance().hasRequiredKit(damager, KitManager.getInstance().valueOf("Anchor")) || KitManager.getInstance().hasRequiredKit(damaged, KitManager.getInstance().valueOf("Anchor"))) {
			double damage = event.getDamage();
			
			event.setCancelled(true);
			
			damaged.damage(damage);
		}
		
	}
	
}
