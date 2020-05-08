package fusion.kits.listeners;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fusion.kits.utils.KitManager;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.RegionManager;

/**
 * 
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class VampireEvent implements Listener {

	@EventHandler
	public void onVampire(EntityDamageByEntityEvent e) {

		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getDamager() instanceof Player)) return;

		Player player = (Player) e.getDamager();
		Player hit = (Player) e.getEntity();

		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Vampire")))
			return;
		
		if (mKitUser.getInstance(player).isInGladiatorArena())
			return;
		
		if (RegionManager.getInstance().isInProtectedRegion(player)) return;

		Random r = new Random();
		
		if (r.nextInt(8) != 1) return;
		
		hit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 4, 1));
		hit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 2, 2));
		hit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 2));
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 2));

	}
	
	@EventHandler
	public void onVampireKill(EntityDeathEvent e) {
		
		
		
		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getEntity().getKiller() instanceof Player)) return;
		
		Player killer = (Player) e.getEntity().getKiller();
		Player dp = (Player) e.getEntity();
		
		if (!KitManager.getInstance().hasRequiredKit(killer, KitManager.getInstance().valueOf("Vampire")))
			return;

		
		killer.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 1));
		killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 10, 1));
		
		Chat.getInstance().messagePlayer(killer, "&cYou finish " + dp.getName() +  " off with ease, you feel the power in your veins.");
		
	}
	
}