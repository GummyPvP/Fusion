package fusion.events.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import fusion.events.Event;
import fusion.events.events.arenas.LMSArena;
import fusion.events.utils.EventLeaveReason;
import fusion.events.utils.EventState;
import fusion.events.utils.EventType;
import fusion.kits.utils.KitManager;
import fusion.listeners.CombatLog;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Created on Dec 23, 2016 by Jeremy Gooch.
	 * 
	 */

public class LMS extends Event implements Listener {
	
	private LMSArena arena;
	private String hostName;
	private EventState state = EventState.WAITING_FOR_PLAYERS;
	
	private List<String> players = new ArrayList<String>();
	
	public LMS(LMSArena arena, String hostName) {
		
		this.arena = arena;
		this.hostName = hostName;
		
	}
	
	@Override
	public String getName() {
		return "LMS";
	}
	
	@Override
	public List<String> getPlayers() {
		return players;
	}
	
	@Override
	public String getHostPlayerName() {
		return hostName;
	}

	@Override
	public int getNeededPlayers() {
		return 2;
	}

	@Override
	public int getMaxPlayers() {
		return 4;
	}

	@Override
	public Rank getRank() {
		return Rank.SLIME;
	}

	@Override
	public EventState getState() {
		return state;
	}
	
	@Override
	public void setState(EventState state) {
		this.state = state;
	}

	@Override 
	public EventType getType() {
		return EventType.LMS;
	}
	
	@Override
	public LMSArena getArena() {
		return arena;
	}
	
	public static ItemStack getItemGUI() {
		return new ItemStack(Material.DIAMOND_SWORD);
	}
	
	int numberOfSeconds = 0; // We'll use this to count how many seconds the event has been in the STARTING state
	
	int timeToCountdown = 10;
	
	@Override
	public void update() {
		
		if (getState() == EventState.STARTING) {
			
			if (numberOfSeconds < timeToCountdown) {
				
				numberOfSeconds++; // Since this method is only going to be called once every second, this should work out fine. Further testing will be needed to ensure this doesn't fail
				
				messagePlayers("&3The event will start in " + ((timeToCountdown + 1) - numberOfSeconds) + (((timeToCountdown + 1) - numberOfSeconds) == 1 ? " second" : " seconds"));
				
				return;
			}
			
			setState(EventState.RUNNING);
			
			// teleport players to main spawn, apply kit
			
			for (String name : getPlayers()) {
				
				Player player = Bukkit.getPlayer(name);
				
				if (player == null) {
					
					players.remove(name);
					
					continue;
				}
				
				if (CombatLog.getInstance().isInCombat(player)) {
					
					removePlayer(player, EventLeaveReason.LEAVE);
					
					Chat.getInstance().messagePlayer(player, "&cYou are disqualified from the event because you are in combat!"); // should just teleport them to a lobby or something
					
					continue;
				}
				
				player.teleport(getArena().getMainSpawn());
				
				KitManager.getInstance().valueOf("PVP").apply(player);
				
			}
			
		} else numberOfSeconds = 0;
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		
		Player player = e.getEntity();
		
		if (!getPlayers().contains(player.getName())) return;
		
		removePlayer(player, EventLeaveReason.DEATH);
		
		handleEvent();
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		if (!getPlayers().contains(e.getPlayer().getName())) return;
		
		removePlayer(e.getPlayer(), EventLeaveReason.DISCONNECT);
		
		handleEvent();
		
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		
		if (!getPlayers().contains(e.getPlayer().getName())) return;
		
		removePlayer(e.getPlayer(), EventLeaveReason.KICK);
		
		handleEvent();
		
	}
	
	private void handleEvent() {
		
		if (getAmountOfPlayers() != 1) return;
		
		Player winner = Bukkit.getPlayer(getPlayers().get(0));
		Player host = Bukkit.getPlayer(getHostPlayerName());
		setState(EventState.FINISHED);
		
		if (host != null) {
			
			mKitUser.getInstance(host).addCandies(200.0);
			
			Chat.getInstance().messagePlayer(host, "&aYou have been rewarded 200 candies for hosting the LMS event!");
			
		}
		
		if (winner == null) return; // somehow he's gone? just don't do anything
		
		Chat.getInstance().broadcastMessage("&a&l" + winner.getName() + " was the last man standing and wins the LMS event!");
		
		mKitUser.getInstance(winner).addCandies(500.0);
		
		Chat.getInstance().messagePlayer(winner, "&aYou have been rewarded 500 candies for winning the LMS event!");
		
	}

}