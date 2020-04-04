package fusion.kits.listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.RegionManager;

/**
 * 
 * Copyright GummyPvP. Created on Jun 5, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class EndermageEvent implements Listener {

	private static final int COOLDOWN_TIME = 10;
	private static final int INVINCIBLE_TIME = 5;
	private static final double RADIUS = 5.0;
	
	private Map<String, Long> cooldownTime = new HashMap<String, Long>();
	private Set<String> safePlayers = new HashSet<String>();

	@EventHandler
	public void onEndermage(BlockPlaceEvent e) {

		Player player = e.getPlayer();

		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Endermage")))
			return;

		if (mKitUser.getInstance(player).isInGladiatorArena())
			return;

		if (e.getBlock().getType() != Material.END_PORTAL_FRAME)
			return;

		if (RegionManager.getInstance().isInProtectedRegion(player))
			return;

		if (!startEndermageDelay(player)) {
			
			long elapsedTimeSinceAction = (System.currentTimeMillis() - cooldownTime.get(player.getName()));
			
			long remainingTime = COOLDOWN_TIME - TimeUnit.MILLISECONDS.toSeconds(elapsedTimeSinceAction);
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "You can use your Endermage ability in "
					+ remainingTime + (remainingTime == 1 ? " second" : " seconds") + "!");
			
			return;
		}
		
		startEndermageInvincibleTimer(player);
		
		int amount = 0;

		for (Entity entities : player.getNearbyEntities(RADIUS, 256.0, RADIUS)) {

			if (!(entities instanceof Player))
				continue;

			Player teleporter = (Player) entities;

			if (teleporter.getGameMode() == GameMode.CREATIVE)
				continue;

			if (RegionManager.getInstance().isInProtectedRegion(teleporter))
				continue;

			if (!startEndermageInvincibleTimer(teleporter)) // don't TP players who have recently been TP'd
				continue;
			
			teleporter.teleport(e.getBlock().getLocation());
			player.teleport(e.getBlock().getLocation());

			Chat.getInstance().messagePlayers("&5The Endermage's spell has been cast... you have "
					+ INVINCIBLE_TIME + " seconds of invincibility!", player, teleporter);

			amount++;

		}

		Chat.getInstance().messagePlayer(player,
				"&c&lYou have teleported " + amount + (amount == 1 ? " person" : " people") + "!");



	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {

		if (!(e.getEntity() instanceof Player))
			return;

		if (isInvincible((Player) e.getEntity())) {

			e.setCancelled(true);

		}

	}
	
	public boolean startEndermageDelay(Player player) {
		
		if (cooldownTime.containsKey(player.getName())) {
			
			long actionTime = cooldownTime.get(player.getName());
			
			if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - actionTime) >= COOLDOWN_TIME) {
				cooldownTime.put(player.getName(), System.currentTimeMillis());
				return true;
			}
			
			return false;
			
		}
		
		cooldownTime.put(player.getName(), System.currentTimeMillis());
		
		return true;
		
	}

	public boolean isInvincible(Player player) {
		return safePlayers.contains(player.getName());
	}

	public boolean startEndermageInvincibleTimer(Player player) {
		
		if (safePlayers.contains(player.getName())) return false;
		
		safePlayers.add(player.getName());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Fusion.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				safePlayers.remove(player.getName());
				
				Chat.getInstance().messagePlayer(player, "&cYou are no longer protected from the Endermage's spell!");
				
			}
		}, 20 * INVINCIBLE_TIME);
		
		return true;
		
	}

}
