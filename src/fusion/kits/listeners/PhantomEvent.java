package fusion.kits.listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;

public class PhantomEvent implements Listener {
	
	private static final int COOLDOWN_TIME = 10;
	private static final int FLIGHT_TIME = 5;
	
	private Map<String, Long> cooldownTimes = new HashMap<String, Long>();
	private Set<String> inFlightPlayers = new HashSet<String>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Phantom")))
			return;

		if (e.getItem() == null) 
			return;
		
		if (e.getHand() != EquipmentSlot.HAND) 
			return;
		
		if (e.getItem().getType() != Material.FEATHER)
			return;
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.PHYSICAL)
			return;
		
		e.setCancelled(true);
		
		if (player.isFlying())
			return;
		
		if (flight(player)) {
			inFlightPlayers.add(player.getName());
		}
		
	}
	
	@EventHandler
	public void onFallDamage(EntityDamageEvent event) {
		
		if (!(event.getEntity() instanceof Player)) return;
		if (event.getCause() != DamageCause.FALL) return;
		if (!KitManager.getInstance().hasRequiredKit((Player) event.getEntity(), KitManager.getInstance().valueOf("Phantom"))) return;
		
		Player player = (Player) event.getEntity();
		
		if (inFlightPlayers.contains(player.getName())) {
			player.setHealth(0.0);
			Chat.getInstance().messagePlayer(player, "&cYou fell out of the sky! Try landing next time");
			inFlightPlayers.remove(player.getName());
		}
		
	}
	
	private boolean flight(Player player) {
		
		if (cooldownTimes.containsKey(player.getName())) {
			
			long elapsedTime = System.currentTimeMillis() - cooldownTimes.get(player.getName());
			long elapsedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
			long remainingTime = COOLDOWN_TIME - elapsedTimeInSeconds;
			
			if (elapsedTimeInSeconds < COOLDOWN_TIME) {
				Chat.getInstance().messagePlayer(player, "&cYou can use your Phantom ability in " + remainingTime + (remainingTime == 1 ? " second!" : " seconds!"));
				return false;
			}
		}
		
		cooldownTimes.put(player.getName(), System.currentTimeMillis());
		
		player.setAllowFlight(true);
		player.setFlying(true);
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0f, 1.0f);
		
		Chat.getInstance().messagePlayer(player, "&cYou're airborne! Make sure to land before you fall out of the sky");
		
		for (Entity entities : player.getNearbyEntities(5.0, 5.0, 5.0)) {
			
			if (!(entities instanceof Player)) continue;
			
			Player entity = (Player) entities;
			Chat.getInstance().messagePlayer(entity, "&c&lA Phantom just started flying nearby!");
			
		}
		
		Bukkit.getScheduler().runTaskLater(Fusion.getInstance(), new Runnable() {

			@Override
			public void run() {
				
				player.setFlying(false);
				player.setAllowFlight(false);
				
				if (!player.isOnGround()) {
					inFlightPlayers.add(player.getName());
				}
				
			}
			
		}, 20 * FLIGHT_TIME);
		
		return true;
		
	}

}
