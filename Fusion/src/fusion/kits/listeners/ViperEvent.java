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

public class ViperEvent implements Listener {

	@EventHandler
	public void onViper(EntityDamageByEntityEvent e) {

		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getDamager() instanceof Player)) return;

		Player player = (Player) e.getDamager();
		Player hit = (Player) e.getEntity();

		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Viper")))
			return;
		if (mKitUser.getInstance(player).isInGladiatorArena())
			return;
		
		if (RegionManager.getInstance().isInProtectedRegion(player)) return;

		Random r = new Random();

		if (r.nextInt(3) == 1)
			hit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));

	}

}
