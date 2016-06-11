package fusion.events.lms;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fusion.events.utils.Countdown;
import fusion.events.utils.Event;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;
import mpermissions.utils.permissions.Rank;

/**
 * 
 * Copyright GummyPvP. Created on Jun 4, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class LMS extends Event implements Listener {

	private LMS() {
	}

	private static LMS instance = new LMS();

	public static LMS getInstance() {
		return instance;
	}

	private Set<String> players = new HashSet<String>();
	private LMSState state;

	public LMSState getState() {
		return state;
	}

	public void setState(LMSState state) {
		this.state = state;
	}

	public Set<String> getPlayers() {
		return players;
	}

	public int getSize() {
		return players.size();
	}

	public void addPlayer(Player player) {
		players.add(player.getName());

		messagePlayers(Chat.SECONDARY_BASE + player.getName() + " joined.");

	}

	public void removePlayer(Player player) {
		players.remove(player.getName());

		messagePlayers(Chat.SECONDARY_BASE + player.getName() + " left.");
	}

	public void messagePlayers(String message) {

		for (String string : players) {

			if (Bukkit.getPlayer(string) != null) {

				Chat.getInstance().messagePlayer(Bukkit.getPlayer(string), ChatColor.GOLD + "[LMS] " + message);

			}

		}

	}

	@Override
	public String getName() {
		return "LMS";
	}

	@Override
	public boolean areKitsEnabled() {
		return false;
	}

	@Override
	public Rank getRankRequired() {
		return Rank.HARIBO;
	}

	@Override
	public int getNeededSize() {
		return 5;
	}

	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, Fusion.getInstance());
	}

	@Override
	public void start(String userStarting) {
		
		setState(LMSState.WAITING);
		
		// start game loop (for broadcasts and stuff)

		new LMSTimer("LMS", 10, ChatColor.GOLD + "[Event] " + Chat.SECONDARY_BASE + "LMS will start in %d second(s)!").runTaskTimer(Fusion.getInstance(), 0, 20);
		
//		new Countdown("LMS", 10, ChatColor.GOLD + "[Event] " + Chat.SECONDARY_BASE + "LMS will start in %d second(s)!")
//				.runTaskTimer(Main.getInstance(), 0, 20);

	}

	/**
	 * 
	 * Listeners
	 * 
	 */

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		getPlayers().remove(e.getPlayer().getName());
		
		messagePlayers(Chat.SECONDARY_BASE + e.getPlayer().getName() + " left.");

		if (getState() != LMSState.WAITING)
			return;

		if (getSize() < getNeededSize()) {

			if (LMSTimer.getInstance("LMS") == null)
				return;

			LMSTimer.getInstance("LMS").cancel();

			messagePlayers(Chat.IMPORTANT_COLOR + "Waiting for players... (" + getSize() + "/" + getNeededSize() + ")");

		}

	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {

		getPlayers().remove(e.getPlayer().getName());
		
		messagePlayers(Chat.SECONDARY_BASE + e.getPlayer().getName() + " left.");

		if (getState() != LMSState.WAITING)
			return;

		if (getSize() < getNeededSize()) {

			if (LMSTimer.getInstance("LMS") == null)
				return;

			LMSTimer.getInstance("LMS").cancel();

			messagePlayers(Chat.IMPORTANT_COLOR + "Waiting for players... (" + getSize() + "/" + getNeededSize() + ")");

		}

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		getPlayers().remove(e.getEntity().getName());
		
		messagePlayers(Chat.SECONDARY_BASE + e.getEntity().getName() + " died. (" + getSize() + " remaining.");
		
	}
}

class LMSTimer extends Countdown {

	public LMSTimer(String id, int count, String message) {
		super(id, count, message);
	}
	
	@Override
	public void run() {
		
		if (LMS.getInstance().getState() == LMSState.WAITING) return;
		
		if (time == 0) {
			
			cancel();
			
			return;
		}
		
		Bukkit.broadcastMessage(String.format(message, time));
		
		time--;
		
	}
	
}
