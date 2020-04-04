package fusion.kits.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.RegionManager;

/**
 * 
 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class ThorEvent implements Listener {

	private Map<String, Integer> cooldownScheduler = new HashMap<String, Integer>();
	private Map<String, Integer> cooldownHandler = new HashMap<String, Integer>();

	private static final int COOLDOWN_TIME = 6;

	@EventHandler
	public void onThor(PlayerInteractEvent e) {

		Player player = e.getPlayer();

		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Thor")))
			return;

		if (e.getItem() == null)
			return;
		
		if (e.getItem().getType() != Material.GOLDEN_AXE)
			return;

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		if (RegionManager.getInstance().isInProtectedRegion(player))
			return;
		if (mKitUser.getInstance(player).isInGladiatorArena())
			return;

		if (!isInThorTimer(player)) {

			startThorDelay(player);

			player.getWorld().strikeLightning(e.getClickedBlock().getLocation());

			return;
		}

		Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "You can use your Thor ability in "
				+ getThorTime(player) + (getThorTime(player) == 1 ? " second" : " seconds") + "!");

	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		
		if (!(e.getEntity() instanceof Player)) return;
		
		if (!e.getCause().equals(DamageCause.LIGHTNING)) return;
		
		Player player = (Player) e.getEntity();
		
		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Thor"))) { // damage them more so thor isn't useless
			
			e.setDamage(e.getFinalDamage() * 3.5); // use final damage to account for armor
			player.setVelocity(player.getEyeLocation().getDirection().multiply(-1.5)); // should throw them backwards
			
			return;
		}
		
		e.setCancelled(true); // don't allow them to be hurt by lightning cuz they're thor
		
		
	}

	public boolean isInThorTimer(Player p) {

		return cooldownHandler.containsKey(p.getName());

	}

	public int getThorTime(Player p) {

		return cooldownHandler.get(p.getName());

	}

	public void startThorDelay(final Player p) {

		cooldownHandler.put(p.getName(), COOLDOWN_TIME);

		cooldownScheduler.put(p.getName(),
				Bukkit.getScheduler().scheduleSyncRepeatingTask(Fusion.getInstance(), new Runnable() {

					public void run() {

						if (cooldownHandler.get(p.getName()) <= 1) {

							cooldownHandler.keySet().remove(p.getName());

							Chat.getInstance().messagePlayer(p,
									Chat.SECONDARY_BASE + "You may now use your Thor ability!");

							Bukkit.getScheduler().cancelTask(cooldownScheduler.get(p.getName()));
						} else
							cooldownHandler.replace(p.getName(), cooldownHandler.get(p.getName()),
									(cooldownHandler.get(p.getName()) - 1));
					}

				}, 0L, 20L));
	}
}
