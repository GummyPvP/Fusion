package fusion.utils.warps;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.Chat;
import fusion.utils.ConfigManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class WarpManager {
	
	private WarpManager() { }
	
	private static WarpManager instance = new WarpManager();
	
	public static WarpManager getInstance() {
		
		return instance;
		
	}
	
	private List<Warp> warps = new ArrayList<Warp>();
	
	public List<Warp> getWarps() {
		return warps;
	}
	
	public Warp getWarp(String name) {
		
		for (Warp warp : warps) {
			
			if (warp.getName().equalsIgnoreCase(name)) return warp;
			
		}
		
		return null;
		
	}
	
	public boolean warpExists(String name) {
		
		return getWarp(name) != null;
		
	}
	
	public boolean registerWarp(String name, Location location, ItemStack item) {
		
		if (warpExists(name)) return false;
		
		Warp warp = new Warp(name, location, item);
		
		warps.add(warp);
		
		return true;
		
	}
	
	public void sendPlayer(Player player, Warp warp) {
		
		// if player is incombat log, no no
		
		player.teleport(warp.getLocation());
		
		Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You've been teleported to warp '" + warp.getName() + "'!");
		
	}
	
	public boolean deleteWarp(String name) {
		
		if (getWarp(name) == null) return false;
		
		warps.remove(getWarp(name));
		
		ConfigManager.getWarpsFile().set("warps." + name, null);
		
		return true;
		
	}
	
	private void loadWarp(String name) {
		
		if (!ConfigManager.getWarpsFile().contains("warps." + name)) return;
		
		World world;
		int x, y, z;
		float yaw, pitch;
		ItemStack item;
		
		world = Bukkit.getWorld(ConfigManager.getWarpsFile().getString("warps." + name + ".world"));
		x = ConfigManager.getWarpsFile().getInt("warps." + name + ".x");
		y = ConfigManager.getWarpsFile().getInt("warps." + name + ".y");
		z = ConfigManager.getWarpsFile().getInt("warps." + name + ".z");
		yaw = ConfigManager.getWarpsFile().getFloat("warps." + name + ".yaw");
		pitch = ConfigManager.getWarpsFile().getFloat("warps." + name + ".pitch");
		item = ConfigManager.getWarpsFile().getItemStack("warps." + name + ".item");
		
		Location location = new Location(world, x, y, z, yaw, pitch);
		
		registerWarp(name, location, item);
		
	}
	
	public void loadWarps() {
		
		if (ConfigManager.getWarpsFile().getSection("warps") == null) return;
		
		for (String warpNames : ConfigManager.getWarpsFile().getSection("warps").getKeys(false)) {
			
			loadWarp(warpNames);
			
		}
		
	}
	
	private void saveWarp(Warp warp) {
		
		String name = warp.getName();
		Location location = warp.getLocation();
		
		ConfigManager.getWarpsFile().set("warps." + name + ".world", location.getWorld().getName());
		ConfigManager.getWarpsFile().set("warps." + name + ".x", location.getBlockX());
		ConfigManager.getWarpsFile().set("warps." + name + ".y", location.getBlockY());
		ConfigManager.getWarpsFile().set("warps." + name + ".z", location.getBlockZ());
		ConfigManager.getWarpsFile().set("warps." + name + ".yaw", location.getYaw());
		ConfigManager.getWarpsFile().set("warps." + name + ".pitch", location.getPitch());
		ConfigManager.getWarpsFile().set("warps." + name + ".item", warp.getInventoryItem());
		
	}
	
	public void saveWarps() {
		
		for (Warp currentWarp : warps) {
			
			saveWarp(currentWarp);
			
		}
		
	}

}
