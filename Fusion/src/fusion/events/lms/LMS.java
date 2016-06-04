package fusion.events.lms;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fusion.events.utils.Event;
import fusion.main.Main;
import fusion.utils.chat.Chat;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 4, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class LMS extends Event implements Listener {
	
	private LMS() { }
	
	private static LMS instance = new LMS();
	
	public static LMS getInstance() {
		return instance;
	}
	
	private Set<String> players = new HashSet<String>();
	
	public Set<String> getPlayers() {
		return players;
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
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	@Override
	public void start(String userStarting) {
		
		
		
	}
	
	/**
	 * 
	 * Listeners
	 * 
	 */
}
