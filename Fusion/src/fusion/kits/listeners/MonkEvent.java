package fusion.kits.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import fusion.kits.utils.KitManager;
import fusion.kits.utils.kitutils.GladiatorManager;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.RegionManager;

/**
	 * 
	 * Created on Apr 6, 2018 by Jeremy Gooch.
	 * 
	 */

public class MonkEvent implements Listener {
	
	private static final int COOLDOWN_TIME = 6;
	
	private Map<String, Long> cooldownTimes = new HashMap<String, Long>();
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		
		if (!KitManager.getInstance().hasRequiredKit(e.getPlayer(), KitManager.getInstance().valueOf("Monk")))
			return;
		
		if (!(e.getRightClicked() instanceof Player)) 
			return;
		
		Player player = e.getPlayer();
		Player clicked = (Player) e.getRightClicked();
		
		if (RegionManager.getInstance().isInProtectedRegion(player))
			return;
		
		if (RegionManager.getInstance().isInProtectedRegion(clicked)) 
			return;
		
		if (GladiatorManager.getInstance().getArena(player) != null)
			return;
		
		if (player.getItemInHand() == null) 
			return;
		
		if (player.getItemInHand().getType() != Material.BLAZE_ROD) 
			return;
		
		if (!disarm(player, clicked))
			return;
		
		Chat.getInstance().messagePlayer(player, "&aYou used your Monk ability to swap " + clicked.getName() + "'s items!");
		Chat.getInstance().messagePlayer(clicked, "&c&lYou were disarmed by a Monk!");
		
	}
	
	private boolean disarm(Player monk, Player disarmee) {
		
		Long cooldownTime = cooldownTimes.get(monk.getName());
		
		if (cooldownTime != null) {
			
			Long elapsedTime = System.currentTimeMillis() - cooldownTime;
			
			Long elapsedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
			
			if (elapsedTimeInSeconds < COOLDOWN_TIME) {
				
				Long remainingTime = (COOLDOWN_TIME - elapsedTimeInSeconds);
				
				Chat.getInstance().messagePlayer(monk, "&cYou can use your Monk ability in " + remainingTime + (remainingTime == 1 ? " second" : " seconds!"));
				
				return false;
			}
			
		}
		
		ItemStack item = disarmee.getItemInHand();
		
		int originalItemSlot = disarmee.getInventory().getHeldItemSlot();
		
		if (item == null) {
			item = new ItemStack(Material.AIR); // we don't want NPE's, but it's fine to move air
		}
		
		Random random = new Random();
		
		int itemSwitchSlot = random.nextInt(disarmee.getInventory().getSize());
		
		ItemStack itemToSwap = disarmee.getInventory().getItem(itemSwitchSlot);
		
		if (itemToSwap == null) {
			itemToSwap = new ItemStack(Material.AIR); // swap air
		}
		
		disarmee.getInventory().setItem(itemSwitchSlot, item);
		disarmee.getInventory().setItem(originalItemSlot, itemToSwap);
		
		disarmee.updateInventory();
		
		cooldownTimes.put(monk.getName(), System.currentTimeMillis());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Fusion.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				cooldownTimes.remove(monk.getName());
				Chat.getInstance().messagePlayer(monk, "&aYou can use your Monk ability again!");
				
			}
		}, 20 * COOLDOWN_TIME);
		
		return true;
		
	}

}
