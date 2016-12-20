package fusion.kits.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
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

public class SharkEvent implements Listener {

	@EventHandler
	public void onViper(PlayerMoveEvent e) {

		
		Player p = (Player) e.getPlayer();

		if (!KitManager.getInstance().hasRequiredKit(p, KitManager.getInstance().valueOf("Shark")))
			return;
		
		if (mKitUser.getInstance(p).isGlad())
			return;
		
		if (p.getLocation().getBlock().isLiquid() == false) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 0));
			return;
			
		}
		
		if (p.getLocation().getBlock().isLiquid() == true) {
			for (PotionEffect effect : p.getActivePotionEffects()) {
				
				p.removePotionEffect(effect.getType());
				p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 300, 0));
				return;
			}
		}
		
	}

}
