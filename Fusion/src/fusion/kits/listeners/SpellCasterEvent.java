package fusion.kits.listeners;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fusion.kits.utils.KitManager;
import fusion.utils.mKitUser;
import fusion.utils.protection.RegionManager;

/**
 * 
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class SpellCasterEvent implements Listener {

	@EventHandler
	public void onSpellCaster(EntityDamageByEntityEvent e) {

		if (!(e.getEntity() instanceof Player))
			return;
		if (!(e.getDamager() instanceof Player))
			return;

		Player player = (Player) e.getDamager();
		Player hit = (Player) e.getEntity();

		
		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("SpellCaster")))
			return;

		if (mKitUser.getInstance(player).isInGladiatorArena())
			return;
		
		if (RegionManager.getInstance().isInProtectedRegion(player))
			return;

		Random r = new Random();

		if (r.nextInt(10) == 1) {
			hit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 4, 2));
			
		}
		if (r.nextInt(10) == 2) {
			hit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 2, 1));
		}
		if (r.nextInt(10) == 2) {
			hit.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 3, 1));
		}
		if (r.nextInt(10) == 3) {
			hit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 1));
		}
		if (r.nextInt(10) == 2) {
			hit.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 2, 0));
		}
	}
}
