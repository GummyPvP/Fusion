package fusion.utils.spawn;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fusion.utils.Chat;
import fusion.utils.ConfigManager;

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
	int radius;
	Set<UUID> spawnProtection = new HashSet<UUID>();
	
	public void setLocation(Location location) {
		
		this.location = location;
		
	}
	
	public Location getLocation() {
		
		return location;
		
	}
	
	public void teleport(Player p) {
		
		if (location == null) {
			
			Chat.getInstance().messagePlayer(p, Chat.BASE_COLOR + "The spawn point is not set up. Please contact an administrator.");
			
			return;
		}
		
		// start cooldown
		
		p.teleport(location);
		
	}
	
	public boolean inBounds(Location location) {
		
		if (location.distance(location) <= radius) return true;
		
		return false;
		
	}
	
	public void save() {
		
		if (location == null) {
			
			System.out.println("Spawn wasn't set, wasn't successfully saved.");
			return;
		}
		
		ConfigManager.getSpawnFile().set("spawn.world", location.getWorld().getName());
		ConfigManager.getSpawnFile().set("spawn.x", location.getX());
		ConfigManager.getSpawnFile().set("spawn.y", location.getY());
		ConfigManager.getSpawnFile().set("spawn.z", location.getZ());
		ConfigManager.getSpawnFile().set("spawn.yaw", location.getYaw());
		ConfigManager.getSpawnFile().set("spawn.pitch", location.getPitch());
		ConfigManager.getSpawnFile().set("spawn.radius", radius);
		
		System.out.println("Spawn saved successfully.");
		
	}
	
	public void load() {
		
		if (ConfigManager.getSpawnFile().getSection("spawn") == null) return;
		
		World world;
		int x, y, z, radius;
		float yaw, pitch;
		
		world = Bukkit.getWorld(ConfigManager.getSpawnFile().get("spawn.world").toString());
		x = ConfigManager.getSpawnFile().get("spawn.x");
		y = ConfigManager.getSpawnFile().get("spawn.y");
		z = ConfigManager.getSpawnFile().get("spawn.z");
		yaw = ConfigManager.getSpawnFile().get("spawn.yaw");
		pitch = ConfigManager.getSpawnFile().get("spawn.pitch");
		radius = ConfigManager.getSpawnFile().get("spawn.radius");
		
		this.location = new Location(world, x, y, z, yaw, pitch);
		this.radius = radius;
	}
}
