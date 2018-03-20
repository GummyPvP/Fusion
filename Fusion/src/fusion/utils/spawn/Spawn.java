package fusion.utils.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fusion.listeners.CombatLog;
import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.ProtectedRegion;
import fusion.utils.editing.regions.RegionManager;

/**
 * 
 * Copyright GummyPvP. Created on May 16, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Spawn {

	private Spawn() {
	}

	private static Spawn instance = new Spawn();

	public static Spawn getInstance() {

		return instance;

	}

	Location location;
	ProtectedRegion region;

	public void setLocation(Location location) {

		this.location = location;

		location.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());

		this.region = (ProtectedRegion) RegionManager.getInstance()
				.getSmallestRegion(RegionManager.getInstance().getRegions(location.toVector()));
		
	}

	public Location getLocation() {

		return location;

	}

	public ProtectedRegion getRegion() {
		return region;
	}
	public void forceTP(Player p) {
		
		if (location == null) {

			Chat.getInstance().messagePlayer(p,
					Chat.IMPORTANT_COLOR + "The spawn point is not set up. Please contact an administrator.");

			return;
		}
		
		p.teleport(location);

		Chat.getInstance().messagePlayer(p, Chat.SECONDARY_BASE + "Teleported to the spawnpoint!");
		
	}

	public void teleport(Player p) {

		if (location == null) {

			Chat.getInstance().messagePlayer(p,
					Chat.IMPORTANT_COLOR + "The spawn point is not set up. Please contact an administrator.");

			return;
		}

		if (CombatLog.getInstance().isInCombat(p)) {

			Chat.getInstance().messagePlayer(p, Chat.IMPORTANT_COLOR + "You are in combat!");

			return;
		}

		if (mKitUser.getInstance(p).hasKit()) {

			Chat.getInstance().messagePlayer(p, Chat.IMPORTANT_COLOR + "To go back to spawn, please type /clearkit");

			return;
		}

		p.teleport(location);

		Chat.getInstance().messagePlayer(p, Chat.SECONDARY_BASE + "Teleported to the spawnpoint!");

	}

	public void save() {

		if (location == null) {

			System.out.println("Spawn wasn't set, wasn't successfully saved.");
			
			return;
		}

		Fusion.getInstance().getSpawnFile().set("spawn.world", location.getWorld().getName());
		Fusion.getInstance().getSpawnFile().set("spawn.x", location.getX());
		Fusion.getInstance().getSpawnFile().set("spawn.y", location.getY());
		Fusion.getInstance().getSpawnFile().set("spawn.z", location.getZ());
		Fusion.getInstance().getSpawnFile().set("spawn.yaw", location.getYaw());
		Fusion.getInstance().getSpawnFile().set("spawn.pitch", location.getPitch());

		Fusion.getInstance().getSpawnFile().save();
		
		System.out.println("Spawn saved successfully.");

	}

	public void load() {

		if (!Fusion.getInstance().getSpawnFile().contains("spawn"))
			return;

		World world;
		double x, y, z;
		float yaw, pitch;

		world = Bukkit.getWorld(Fusion.getInstance().getSpawnFile().getString("spawn.world"));
		x = Fusion.getInstance().getSpawnFile().getDouble("spawn.x");
		y = Fusion.getInstance().getSpawnFile().getDouble("spawn.y");
		z = Fusion.getInstance().getSpawnFile().getDouble("spawn.z");
		yaw = Fusion.getInstance().getSpawnFile().getInt("spawn.yaw");
		pitch = Fusion.getInstance().getSpawnFile().getInt("spawn.pitch");

		this.location = new Location(world, x, y, z, yaw, pitch);

		this.region = (ProtectedRegion) RegionManager.getInstance()
				.getSmallestRegion(RegionManager.getInstance().getRegions(location.toVector()));
	}
}
