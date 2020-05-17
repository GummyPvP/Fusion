package fusion.event.events;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import fusion.event.util.FusionEvent;
import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;

public class LMS extends FusionEvent implements Listener {

	private Set<Player> players;
	
	private BukkitTask taskTimer;
	
	private boolean started;
	
	private boolean spawnInvincibility;
	
	private static final int TICKS_FOR_TIMER = 20;
	
	private Location spawn;
	
	public LMS() {
		started = false;
		players = new HashSet<Player>();
		spawn = new Location(Bukkit.getWorld("world"), 229, 128, 209);
		spawnInvincibility = true;
	}
	
	@Override
	public String getName() {
		return "LMS";
	}

	@Override
	public int getMinimumPlayerCount() {
		return 4;
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
			mKitUser.getInstance(player).clearKit();
			player.teleport(spawn);
			KitManager.getInstance().valueOf("PVP").apply(player);
		}
		
		taskTimer = Bukkit.getScheduler().runTaskTimer(Fusion.getInstance(), new Runnable() {
			
			int invincibleTime = 5;
			
			public void run() {
				if (invincibleTime <= 0) {
					spawnInvincibility = false;
					return;
				}
				
				messageParticipants("&aYou have " + invincibleTime + " seconds of spawn protection remaining!");
				
				invincibleTime--;
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
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		handlePlayer(event.getEntity(), "&e" + event.getEntity().getName() + " &adied and has lost the LMS event! &6&l(%d remain)");
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		handlePlayer(event.getPlayer(), "&e" + event.getPlayer().getName() + " &alogged out and has lost the LMS event! &6&l(%d remain)");
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		handlePlayer(event.getPlayer(), "&e" + event.getPlayer().getName() + " &awas kicked and has lost the LMS event! &6&l(%d remain)");
	}
	
	@EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onDamage(EntityDamageByEntityEvent event) {
		
		if (!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		if (!getPlayers().contains(player)) return;
		
		if (spawnInvincibility) {
			event.setCancelled(true);
			return;
		}
		
		if (event.isCancelled()) {
			event.setCancelled(false);
		}
		
	}
	
	private void handlePlayer(Player player, String message) {
		if (!getPlayers().contains(player)) return;
		
		removePlayer(player);
		
		messageParticipants(String.format(message, getPlayers().size()));
		
		if (getPlayers().size() == 1) {
			Player winner = (Player) getPlayers().toArray()[0];
			Chat.getInstance().broadcastMessage("&7[&eEvent&7] &3" + winner.getName() + " &awon the LMS event!");
			mKitUser.getInstance(winner).addCandies(500.0);
			Chat.getInstance().messagePlayer(winner, "&aYou have been awarded &c500.0 &acandies for your victory!");
			end();
		}
	}

}
