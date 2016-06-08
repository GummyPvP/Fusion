package fusion.utils.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fusion.utils.ConfigManager;
import fusion.utils.chat.Chat;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 16, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Spawn {
	
	private Spawn() { }
	
	private static Spawn instance = new Spawn();
	
	public static Spawn getInstance() {
		
		return instance;
		
	}
	
	Location location;
	ProtectedRegion region;
	
	public void setLocation(Location location) {
		
		this.location = location;
		
		location.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public ProtectedRegion getRegion() {
		return region;
	}
	
	public void teleport(Player p) {
		
		if (location == null) {
			
			Chat.getInstance().messagePlayer(p, Chat.IMPORTANT_COLOR + "The spawn point is not set up. Please contact an administrator.");
			
			return;
		}
		
		// start cooldown
		
		p.teleport(location);
		
		Chat.getInstance().messagePlayer(p, Chat.SECONDARY_BASE + "Teleported to the spawnpoint!");
		
	}
	
	public void save() {
		
		if (location == null) {
			
			System.out.println("Spawn wasn't set, wasn't successfully saved.");
			return;
		}
		
		if (region == null) {
			
			System.out.println("Spawn - No region!");
			
			return;
		}
		
		ConfigManager.getSpawnFile().set("spawn.world", location.getWorld().getName());
		ConfigManager.getSpawnFile().set("spawn.x", location.getX());
		ConfigManager.getSpawnFile().set("spawn.y", location.getY());
		ConfigManager.getSpawnFile().set("spawn.z", location.getZ());
		ConfigManager.getSpawnFile().set("spawn.yaw", location.getYaw());
		ConfigManager.getSpawnFile().set("spawn.pitch", location.getPitch());
		
		System.out.println("Spawn saved successfully.");
		
	}
	
	public void load() {
		
		if (ConfigManager.getSpawnFile().getSection("spawn") == null) return;
		
		World world;
		double x, y, z;
		float yaw, pitch;
		
		world = Bukkit.getWorld(ConfigManager.getSpawnFile().getString("spawn.world"));
		x = ConfigManager.getSpawnFile().getDouble("spawn.x");
		y = ConfigManager.getSpawnFile().getDouble("spawn.y");
		z = ConfigManager.getSpawnFile().getDouble("spawn.z");
		yaw = ConfigManager.getSpawnFile().getInt("spawn.yaw");
		pitch = ConfigManager.getSpawnFile().getInt("spawn.pitch");
		
		this.location = new Location(world, x, y, z, yaw, pitch);
		
		this.region = (ProtectedRegion) RegionManager.getInstance().getSmallestRegion(RegionManager.getInstance().getRegions(location.toVector()));
	}
}
