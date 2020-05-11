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
	public void onSharkMove(PlayerMoveEvent e) {
		
		Player p = (Player) e.getPlayer();

		if (!KitManager.getInstance().hasRequiredKit(p, KitManager.getInstance().valueOf("Shark")))
			return;
		
		if (mKitUser.getInstance(p).isInGladiatorArena())
			return;
		
		if (p.getLocation().getBlock().isLiquid() == false) {
			
			p.removePotionEffect(PotionEffectType.WATER_BREATHING);
			p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
			return;
			
		}
		
		if (p.getLocation().getBlock().isLiquid() == true) {
			
			p.removePotionEffect(PotionEffectType.SLOW);
			p.removePotionEffect(PotionEffectType.NIGHT_VISION);
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
		}
		
	}

}
