package fusion.utils.warps;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.listeners.CombatLog;
import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;

/**
 * 
 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class WarpManager {

	private WarpManager() {
	}

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

			if (warp.getName().equalsIgnoreCase(name))
				return warp;

		}

		return null;

	}

	public boolean warpExists(String name) {

		return getWarp(name) != null;

	}

	public boolean registerWarp(String name, Location location, ItemStack item) {

		if (warpExists(name))
			return false;

		Warp warp = new Warp(name, location, item);

		warps.add(warp);

		return true;

	}

	public void sendPlayer(Player player, Warp warp) {

		if (CombatLog.getInstance().isInCombat(player)) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "You are in combat!");
			
			return;
		}
		
		if (mKitUser.getInstance(player).hasKit()) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "You may not warp with a kit equipped!");
			
			return;
		}

		player.teleport(warp.getLocation());

		Chat.getInstance().messagePlayer(player,
				Chat.SECONDARY_BASE + "You've been teleported to warp '" + warp.getName() + "'!");

	}

	public boolean deleteWarp(String name) {

		if (getWarp(name) == null)
			return false;

		warps.remove(getWarp(name));

		Fusion.getInstance().getWarpsFile().set("warps." + name, null);

		return true;

	}

	private void loadWarp(String name) {

		if (!Fusion.getInstance().getWarpsFile().contains("warps." + name))
			return;

		World world;
		double x, y, z;
		float yaw, pitch;
		ItemStack item;

		world = Bukkit.getWorld(Fusion.getInstance().getWarpsFile().getString("warps." + name + ".world"));
		x = Fusion.getInstance().getWarpsFile().getDouble("warps." + name + ".x");
		y = Fusion.getInstance().getWarpsFile().getDouble("warps." + name + ".y");
		z = Fusion.getInstance().getWarpsFile().getDouble("warps." + name + ".z");
		yaw = Fusion.getInstance().getWarpsFile().getFloat("warps." + name + ".yaw");
		pitch = Fusion.getInstance().getWarpsFile().getFloat("warps." + name + ".pitch");
		item = Fusion.getInstance().getWarpsFile().getItemStack("warps." + name + ".item");

		Location location = new Location(world, x, y, z, yaw, pitch);

		registerWarp(name, location, item);

	}

	public void loadWarps() {

		if (Fusion.getInstance().getWarpsFile().getSection("warps") == null)
			return;

		for (String warpNames : Fusion.getInstance().getWarpsFile().getSection("warps").getKeys(false)) {

			loadWarp(warpNames);

		}

	}

	private void saveWarp(Warp warp) {

		if (warp.getLocation().getWorld() != null) {

			String name = warp.getName();
			Location location = warp.getLocation();

			Fusion.getInstance().getWarpsFile().set("warps." + name + ".world", location.getWorld().getName());
			Fusion.getInstance().getWarpsFile().set("warps." + name + ".x", location.getX());
			Fusion.getInstance().getWarpsFile().set("warps." + name + ".y", location.getY());
			Fusion.getInstance().getWarpsFile().set("warps." + name + ".z", location.getZ());
			Fusion.getInstance().getWarpsFile().set("warps." + name + ".yaw", location.getYaw());
			Fusion.getInstance().getWarpsFile().set("warps." + name + ".pitch", location.getPitch());
			Fusion.getInstance().getWarpsFile().set("warps." + name + ".item", warp.getInventoryItem());

		}
	}

	public void saveWarps() {

		for (Warp currentWarp : warps) {

			saveWarp(currentWarp);

		}

	}

}
