package fusion.kits.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import fusion.kits.utils.KitManager;
import fusion.utils.chat.Chat;

public class NeoEvent implements Listener {
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onProjectileHit(EntityDamageByEntityEvent event) {
		
		if (!(event.getDamager() instanceof Arrow)) return;
		
		Projectile projectile = (Projectile) event.getDamager();
		
		if (!(projectile.getShooter() instanceof Player)) return;
		
		if (!(event.getEntity() instanceof Player)) return;
		
		Player damaged = (Player) event.getEntity();
		Player shooter = (Player) projectile.getShooter();
		
		if (!KitManager.getInstance().hasRequiredKit(damaged, KitManager.getInstance().valueOf("Neo")))
			return;
		
		Arrow arrow = (Arrow) damaged.getWorld().spawnEntity(damaged.getLocation(), EntityType.ARROW);
		Vector velocity = shooter.getLocation().toVector().subtract(arrow.getLocation().toVector()).normalize();
		arrow.setVelocity(arrow.getVelocity().add(velocity).multiply(20));
		
		event.setCancelled(true);
		
		Chat.getInstance().messagePlayer(shooter, "&aNeo just deflected your shot!");
		
	}

}
