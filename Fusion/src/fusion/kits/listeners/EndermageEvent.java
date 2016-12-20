package fusion.kits.listeners;

import java.util.HashMap;
import java.util.Map;

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
import fusion.utils.protection.RegionManager;

/**
 * 
 * Copyright GummyPvP. Created on Jun 5, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class EndermageEvent implements Listener {

	private Map<String, Integer> cooldownScheduler = new HashMap<String, Integer>();
	private Map<String, Integer> cooldownHandler = new HashMap<String, Integer>();

	private Map<String, Integer> invincibleScheduler = new HashMap<String, Integer>();
	private Map<String, Integer> invincibleHandler = new HashMap<String, Integer>();

	private static final int COOLDOWN_TIME = 10;
	private static final int INVINCIBLE_TIME = 5;

	@EventHandler
	public void onEndermage(BlockPlaceEvent e) {

		Player player = e.getPlayer();

		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Endermage")))
			return;

		if (mKitUser.getInstance(player).isGlad())
			return;

		if (e.getBlock().getType() != Material.ENDER_PORTAL_FRAME)
			return;

		if (RegionManager.getInstance().isInProtectedRegion(player))
			return;

		if (!isInEndermageTimer(player)) {

			startEndermageDelay(player);

			startEndermageInvincibleTimer(player);

			int amount = 0;

			for (Entity entities : player.getNearbyEntities(5.0, 256.0, 5.0)) {

				if (!(entities instanceof Player))
					continue;

				Player teleporter = (Player) entities;

				if (teleporter.getGameMode() == GameMode.CREATIVE)
					continue;

				teleporter.teleport(e.getBlock().getLocation());
				player.teleport(e.getBlock().getLocation());

				startEndermageInvincibleTimer(teleporter);

				Chat.getInstance().messagePlayer(teleporter,
						"You have been teleported by an Endermage! You have 5 seconds to run or fight!");

				amount++;

			}

			Chat.getInstance().messagePlayer(player,
					"You have teleported " + amount + (amount == 1 ? " person" : " people") + "!");

			return;
		}

		Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "You can use your Endermage ability in "
				+ getEndermageTime(player) + (getEndermageTime(player) == 1 ? " second" : " seconds") + "!");

	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {

		if (!(e.getEntity() instanceof Player))
			return;

		if (isInvincible((Player) e.getEntity())) {

			e.setCancelled(true);

		}

	}

	public boolean isInEndermageTimer(Player p) {

		return cooldownHandler.containsKey(p.getName());

	}

	public int getEndermageTime(Player p) {

		return cooldownHandler.get(p.getName());

	}

	public void startEndermageDelay(final Player p) {

		cooldownHandler.put(p.getName(), COOLDOWN_TIME);

		cooldownScheduler.put(p.getName(),
				Bukkit.getScheduler().scheduleSyncRepeatingTask(Fusion.getInstance(), new Runnable() {

					public void run() {

						if (cooldownHandler.get(p.getName()) <= 1) {

							cooldownHandler.keySet().remove(p.getName());

							Chat.getInstance().messagePlayer(p,
									Chat.SECONDARY_BASE + "You may now use your Endermage ability!");

							Bukkit.getScheduler().cancelTask(cooldownScheduler.get(p.getName()));
						} else
							cooldownHandler.replace(p.getName(), cooldownHandler.get(p.getName()),
									(cooldownHandler.get(p.getName()) - 1));
					}

				}, 0L, 20L));
	}

	public boolean isInvincible(Player player) {

		return invincibleHandler.containsKey(player.getName());

	}

	public int getInvincibleTime(Player player) {

		if (!isInvincible(player))
			return -1;

		return invincibleHandler.get(player.getName());

	}

	public void startEndermageInvincibleTimer(final Player p) {

		invincibleHandler.put(p.getName(), INVINCIBLE_TIME);

		invincibleScheduler.put(p.getName(),
				Bukkit.getScheduler().scheduleSyncRepeatingTask(Fusion.getInstance(), new Runnable() {

					public void run() {

						if (invincibleHandler.get(p.getName()) <= 1) {

							invincibleHandler.keySet().remove(p.getName());

							Chat.getInstance().messagePlayer(p,
									Chat.SECONDARY_BASE + "You are no longer protected by the Endermage spell!");

							Bukkit.getScheduler().cancelTask(invincibleScheduler.get(p.getName()));
						} else
							invincibleHandler.replace(p.getName(), invincibleHandler.get(p.getName()),
									(invincibleHandler.get(p.getName()) - 1));
					}

				}, 0L, 20L));

	}

}
