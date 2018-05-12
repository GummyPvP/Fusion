package org.mcwarfare.cubecore.koth.gameplay;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.mcwarfare.cubecore.koth.utils.region.Region;
import org.mcwarfare.cubecore.utils.Chat;

/**
	 * 
	 * Created on Jul 12, 2016 by Jeremy Gooch.
	 * 
	 */

public class RegionTracker implements Listener {
	
	// TODO: send them a message when they walk into a KOTH region (just to be more user friendly)
	
	private Region region;
	
	private Set<String> playersInRegion = new HashSet<String>(); // to ensure we don't spam them with messages when they move
	
	public RegionTracker(Region region) {
		
		this.region = region;
		
	}
	
	public Set<String> getPlayersInRegion() {
		return playersInRegion;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		check(e.getPlayer());
		
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		
		check(e.getPlayer());
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		playersInRegion.remove(e.getPlayer().getName());
		
	}
	
	private void check(Player player) {
		
		if (region.getBounds().isInBounds(player.getLocation())) {
			
			if (playersInRegion.contains(player.getName())) return;
			
			playersInRegion.add(player.getName());
			
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
			Chat.getInstance().messagePlayer(player, ChatColor.YELLOW + "Entering KOTH Region: " + ChatColor.GOLD + region.getName());
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
			
			return;
		}
		
		if (playersInRegion.contains(player.getName())) {
			
			playersInRegion.remove(player.getName());
			
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
			Chat.getInstance().messagePlayer(player, ChatColor.YELLOW + "Leaving KOTH Region: " + ChatColor.GOLD + region.getName());
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
			
		}
	}
}
