package fusion.kits.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fusion.kits.utils.KitManager;
import fusion.utils.mKitUser;

/**
 * 
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class WimpEvent implements Listener {

	@EventHandler
	public void onViper(EntityDamageByEntityEvent e) {

		if (!(e.getEntity() instanceof Player)) return;
	
		Player p = (Player) e.getEntity();

		if (!KitManager.getInstance().hasRequiredKit(p, KitManager.getInstance().valueOf("Wimp")))
			return;
		if (mKitUser.getInstance(p).isGlad())
			return;

		if (p.getHealth() <= 8) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 4, 1));
			return;

		}

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {

		if (event.getEntity() instanceof Player) {

			if (event.getEntity().getKiller() instanceof Player) {

				Player killer = event.getEntity().getKiller();

				if (KitManager.getInstance().hasRequiredKit(killer, KitManager.getInstance().valueOf("Wimp"))) {
					killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 7, 1));
				}

			}

		}

	}

}
