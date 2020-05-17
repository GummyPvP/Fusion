package fusion.event.events;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import fusion.event.util.FusionEvent;
import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.ProtectedRegion.HealingItem;

public class Anvil extends FusionEvent implements Listener {

	private Set<Player> players;
	
	private BukkitTask taskTimer;
	
	private boolean started;
	
	private static final int TICKS_FOR_TIMER = 20;
	
	private Location spawn;
	
	private int timer = 0;
	
	private Vector minCorner, maxCorner;
	
	public Anvil() {
		started = false;
		players = new HashSet<Player>();
		spawn = new Location(Bukkit.getWorld("world"), -357, 4, -19);
		
		minCorner = new Vector(-369, 41, -30);
		maxCorner = new Vector(-344, 41, -7);
		
//		minCorner = Vector.getMinimum(minCorner, maxCorner);
//		maxCorner = Vector.getMaximum(minCorner, maxCorner);
	}
	
	@Override
	public String getName() {
		return "Anvil";
	}

	@Override
	public int getMinimumPlayerCount() {
		return 2;
	}

	@Override
	public int getMaximumPlayerCount() {
		return -1;
	}

	@Override
	public Set<Player> getPlayers() {
		return players;
	}

	@Override
	public BukkitTask getTaskTimer() {
		return taskTimer;
	}
	
	@Override
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	@Override
	public boolean isStarted() {
		return started;
	}
	
	public Location getSpawn() {
		return spawn;
	}
	
	public void setSpawn(Location location) {
		this.spawn = location;
	}
	
	@Override
	public void start() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Fusion.getInstance());
		
		for (Player player : getPlayers()) {
			
			if (player == null) {
				removePlayer(player);
				continue;
			}
			
			player.teleport(spawn);
			player.getInventory().clear();
			
			HealingItem item = mKitUser.getInstance(player).getHealingItem();
			
			for (int i = 0; i < player.getInventory().getSize(); i++) {
				player.getInventory().setItem(i, item.getItem());
			}
		}
		
		taskTimer = Bukkit.getScheduler().runTaskTimer(Fusion.getInstance(), new Runnable() {
			
			public void run() {
				timer++;
				
				if (timer % 10 == 0) {
					
					// timer is simply gonna be percentage of anvils that fall (this means each time anvils fall, there will be 10% more anvils compared to the last time they fell)
					
					messageParticipants("&aWave #" + (timer / 10) + " - " + timer + "% anvils");
					
					for (int x = minCorner.getBlockX(); x < maxCorner.getBlockX(); x++) {
						for (int z = minCorner.getBlockZ(); z < maxCorner.getBlockZ(); z++) {
							
							int rInt = new Random().nextInt(100);
							
							if (rInt < timer) {
								Block currentBlock = Bukkit.getWorld("world").getBlockAt(x, minCorner.getBlockY(), z);
								
								if (currentBlock.getType() != Material.AIR) continue;
								
								currentBlock.setType(Material.ANVIL);
								
							}
						}
					}
				}
			}
			
		}, 0, TICKS_FOR_TIMER);
		setStarted(true);
	}
	
	@Override
	public void end() {
		super.end();
		HandlerList.unregisterAll(this);
		
		if (taskTimer != null) {
			taskTimer.cancel();
		}
		
		timer = 0;
	}
	
	@EventHandler
	public void onBlockDamage(EntityChangeBlockEvent event) {
		
		if (!event.getTo().toString().contains("ANVIL")) return;
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		handlePlayer(event.getEntity(), "&e" + event.getEntity().getName() + " &adied and has lost the Anvil event! &6&l(%d remain)");
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		handlePlayer(event.getPlayer(), "&e" + event.getPlayer().getName() + " &alogged out and has lost the Anvil event! &6&l(%d remain)");
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		handlePlayer(event.getPlayer(), "&e" + event.getPlayer().getName() + " &awas kicked and has lost the Anvil event! &6&l(%d remain)");
	}
	
	@EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onDamage(EntityDamageByEntityEvent event) {
		
		if (!(event.getEntity() instanceof Player)) return;
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();
		
		if (!getPlayers().contains(player)) return;
		if (!getPlayers().contains(damager)) return;
		
		event.setCancelled(true); // no pvp in anvil
		
	}
	
	private void handlePlayer(Player player, String message) {
		if (!getPlayers().contains(player)) return;
		
		removePlayer(player);
		
		messageParticipants(String.format(message, getPlayers().size()));
		
		if (getPlayers().size() == 1) {
			Player winner = (Player) getPlayers().toArray()[0];
			Chat.getInstance().broadcastMessage("&7[&eEvent&7] &3" + winner.getName() + " &awon the Anvil event!");
			mKitUser.getInstance(winner).addCandies(500.0);
			Chat.getInstance().messagePlayer(winner, "&aYou have been awarded &c500.0 &acandies for your victory!");
			
			Bukkit.getScheduler().runTaskLater(Fusion.getInstance(), new Runnable() {
				
				public void run() {
					end(); // 10 ticks later so the anvils are for sure cleaned up
				}
				
			}, 10);
		}
	}

}

class AnvilTask extends BukkitRunnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
