package fusion.kits.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;

public class HulkEvent implements Listener {
	
	private static final int COOLDOWN_TIME = 15;
	private static final int IMMOBILE_TIME = 6;
	
	private Map<String, Long> cooldownTimes = new HashMap<String, Long>();
	private Map<String, Long> leaveTimes = new HashMap<String, Long>();
	
	@EventHandler
	public void onInteractWithPlayer(PlayerInteractAtEntityEvent event) {
		
		Player player = event.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Hulk")))
			return;
		
		if (!(event.getRightClicked() instanceof Player)) return;
		
		Player pickedUp = (Player) event.getRightClicked();
		
		if (!pickup(player, pickedUp)) return;
		
		Chat.getInstance().messagePlayer(player, "&aYou used your Hulk ability to pickup " + pickedUp.getName() + "!");
		Chat.getInstance().messagePlayer(pickedUp, "&c&lYou were picked up by a Hulk!");
		
	}
	
	@EventHandler
	public void onPlayerExitVehicle(EntityDismountEvent event) {
		
		if (!(event.getDismounted() instanceof Player)) return;
		if (!(event.getEntity() instanceof Player)) return;
		
		Player hulk = (Player) event.getDismounted();
		Player pickedUp = (Player) event.getEntity();
		
		Long cooldownTime = leaveTimes.get(pickedUp.getName());
		
		if (cooldownTime != null) {
			
			Long elapsedTime = System.currentTimeMillis() - cooldownTime;
			
			Long elapsedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
			
			if (elapsedTimeInSeconds < IMMOBILE_TIME) {
				
				Chat.getInstance().messagePlayer(pickedUp, "&cKeep struggling!");
				
				event.setCancelled(true);
				
				return;
			}
			
		}
		
		leaveTimes.remove(pickedUp.getName());
		
		Chat.getInstance().messagePlayer(hulk, "&cYour target escaped your grasp!");
		Chat.getInstance().messagePlayer(pickedUp, "&cYou escaped!");
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Hulk")))
			return;
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
			return;
		
		if (player.getPassengers().isEmpty()) return;
		
		Player pickedUp = (Player) player.getPassengers().get(0);
		
		leaveTimes.remove(pickedUp.getName());
		
		player.eject();
		pickedUp.eject();
		
		Vector vector = player.getEyeLocation().getDirection();
		
        vector.multiply(2F);
        vector.setY(0.8D);
		
		pickedUp.setVelocity(vector);
		
	}
	
	private boolean pickup(Player hulk, Player pickedUp) {
		
		Long cooldownTime = cooldownTimes.get(hulk.getName());
		
		if (cooldownTime != null) {
			
			Long elapsedTime = System.currentTimeMillis() - cooldownTime;
			
			Long elapsedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
			
			if (elapsedTimeInSeconds < COOLDOWN_TIME) {
				
				Long remainingTime = (COOLDOWN_TIME - elapsedTimeInSeconds);
				
				Chat.getInstance().messagePlayer(hulk, "&cYou can use your hulk ability in " + remainingTime + (remainingTime == 1 ? " second!" : " seconds!"));
				
				return false;
			}
			
		}
		
		hulk.addPassenger(pickedUp);
		
		cooldownTimes.put(hulk.getName(), System.currentTimeMillis());
		leaveTimes.put(pickedUp.getName(), System.currentTimeMillis());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Fusion.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				cooldownTimes.remove(hulk.getName());
				Chat.getInstance().messagePlayer(hulk, "&aYou can use your hulk ability again!");
				
			}
		}, 20 * COOLDOWN_TIME);
		
		return true;
		
	}

}
