package fusion.kits.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fusion.kits.utils.KitManager;
import fusion.kits.utils.kitutils.GladiatorManager;
import fusion.main.Fusion;
import fusion.utils.ItemBuilder;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.RegionManager;

/**
	 * 
	 * Created on Apr 2, 2018 by Jeremy Gooch.
	 * 
	 */

public class AssassinEvent implements Listener {
	
	private Map<String, ItemStack[]> assassinContents = new HashMap<String, ItemStack[]>();
	private Map<String, Long> cooldowns = new HashMap<String, Long>();
	
	private static int SPECIAL_ABILITY_LENGTH = 3;
	private static int COOLDOWN_LENGTH = 20;
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(event.getPlayer(), KitManager.getInstance().valueOf("Assassin")))
			return;

		if (player.getInventory().getItemInMainHand().getType() != Material.GLOWSTONE_DUST)
			return;

		if (RegionManager.getInstance().isInProtectedRegion(player))
			return;
		
		if (GladiatorManager.getInstance().getArena(player) != null)
			return;
		
		if (cooldowns.containsKey(player.getName())) {
			long elapsedTime = System.currentTimeMillis() - cooldowns.get(player.getName());
			
			if (!((TimeUnit.MILLISECONDS.toSeconds(elapsedTime)) >= COOLDOWN_LENGTH)) {
				
				long remainingTime = (COOLDOWN_LENGTH - TimeUnit.MILLISECONDS.toSeconds(elapsedTime));
				
				Chat.getInstance().messagePlayer(player, "&cYou must wait for your Assassin ability to recharge! " + remainingTime + (remainingTime == 1 ? " second remains" : " seconds remain"));
				return;
			}
		}
		
		assassin(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		
		if (!(event.getEntity() instanceof Player)) return;
		
		if (!(event.getDamager() instanceof Player)) return;
		
		Player damaged = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();
		
		if (RegionManager.getInstance().isInProtectedRegion(damaged) || RegionManager.getInstance().isInProtectedRegion(damager))
			return;
		
		if (GladiatorManager.getInstance().getArena(damaged) != null || GladiatorManager.getInstance().getArena(damager) != null)
			return;
		
		if (KitManager.getInstance().hasRequiredKit(damaged, KitManager.getInstance().valueOf("Assassin"))) {
			
			if (!assassinContents.containsKey(damaged.getName())) return; // if they're in assassin mode and they get hit, kill them instantly
			damaged.setHealth(0.0);
			return;
		}
		
		if (KitManager.getInstance().hasRequiredKit(damager, KitManager.getInstance().valueOf("Assassin"))) {
			
			// only instant kill if the assassin is behind the damaged player
			
			if (!assassinContents.containsKey(damager.getName())) return;
			
			Vector c = damaged.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize(); // Get vector between you and other
			Vector d = damaged.getEyeLocation().getDirection(); // Get direction you are looking at
			
			double delta = c.dot(d);
			if (delta > 0){
				damaged.setHealth(0.0);
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(event.getPlayer(), KitManager.getInstance().valueOf("Assassin")))
			return;
		
		if (assassinContents.containsKey(player.getName())) {
			assassinContents.remove(player.getName());
		}
		
	}
	
	private void assassin(Player player) {
		
		Chat.getInstance().messagePlayer(player, "&cIt's time to strike! Go in for the kill!");
		
		assassinContents.put(player.getName(), player.getInventory().getContents());
		
		cooldowns.put(player.getName(), System.currentTimeMillis());
		
		// ADD COOLDOWN
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aHidden Blade").lore("Was difficult to get through U.S. customs :(").build();
		player.getInventory().addItem(sword);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
		
		Bukkit.getScheduler().runTaskLater(Fusion.getInstance(), new Runnable() {
			
			public void run() {
				
				if (!assassinContents.containsKey(player.getName())) return;
				
				if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Assassin"))) {
					assassinContents.remove(player.getName());
					return;
				}
				
				if (player.isDead()) return;
				
				player.getInventory().clear();
				player.getInventory().setContents(assassinContents.get(player.getName()));
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
				assassinContents.remove(player.getName());
				
				Chat.getInstance().messagePlayer(player, "&cYour effects have worn off... try again soon");
				
			}
			
		}, SPECIAL_ABILITY_LENGTH * 20);
	}

}
