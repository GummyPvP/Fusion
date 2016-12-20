package fusion.utils.protection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import fusion.kits.utils.Kit;
import fusion.utils.chat.Chat;

/**
 * 
 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class RegionTracker implements Listener {

	private Region region;

	Set<UUID> onPoint = new HashSet<UUID>();

	public RegionTracker(Region region) {

		this.region = region;

	}

	public Region getRegion() {
		return region;
	}

	public void handleMove(Player player, Vector to) {

		if (!player.isDead() && region.getBounds().inBounds(player.getLocation())
				&& !onPoint.contains(player.getUniqueId())) {

			if (RegionManager.getInstance().getRegions(to).isEmpty())
				return;

			onPoint.add(player.getUniqueId());

			Region newRegion = RegionManager.getInstance()
					.getSmallestRegion(RegionManager.getInstance().getRegions(to));

			if (newRegion instanceof ProtectedRegion) {

				ProtectedRegion protectedRegion = (ProtectedRegion) region;

//				if (protectedRegion.isPVPEnabled()) {
//
//					if (mKitUser.getInstance(player).hasKit() == false) {
//						
//						if (protectedRegion.getName().equalsIgnoreCase("warzone")) {
//
//							if (player.isOp() == false) {
//
//								Spawn.getInstance().teleport(player);
//								
//								onPoint.remove(player.getUniqueId());
//
//							}
//
//						}
//
//					}
//
//					return;
//				}

				StringBuilder kits = new StringBuilder();

				for (Kit kit : protectedRegion.getBlockedKits()) {

					kits.append(kit.getName() + ", ");

				}

				String kitList = "";

				Pattern pattern = Pattern.compile(", $");
				Matcher matcher = pattern.matcher(kits.toString());
				kitList = matcher.replaceAll("");

				player.sendMessage(
						Chat.BASE_COLOR + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
				Chat.getInstance().messagePlayer(player,
						ChatColor.YELLOW + "Entering: " + ChatColor.GOLD + protectedRegion.getName()
								+ (protectedRegion.isPVPEnabled() ? ChatColor.DARK_RED + " (PVP enabled)"
										: Chat.SECONDARY_BASE + " (PVP disabled)"));
				Chat.getInstance().messagePlayer(player,
						ChatColor.YELLOW + "Refills: " + (protectedRegion.areRefillsAllowed()
								? Chat.SECONDARY_BASE + "enabled" : Chat.IMPORTANT_COLOR + "disabled"));
				Chat.getInstance().messagePlayer(player, ChatColor.YELLOW + "Healing Item: " + ChatColor.LIGHT_PURPLE
						+ protectedRegion.getHealingItem().toString());
				Chat.getInstance().messagePlayer(player,
						ChatColor.YELLOW + "Blocked Kits: " + (protectedRegion.getBlockedKits().isEmpty()
								? Chat.SECONDARY_BASE + "none" : Chat.IMPORTANT_COLOR + kitList));
				player.sendMessage(
						Chat.BASE_COLOR + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
			}

		} else if (!player.isDead() && !region.getBounds().inBounds(player.getLocation())
				&& onPoint.contains(player.getUniqueId())) {

			onPoint.remove(player.getUniqueId());

			player.sendMessage(
					Chat.BASE_COLOR + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
			Chat.getInstance().messagePlayer(player,
					ChatColor.YELLOW + "Leaving: " + ChatColor.GOLD + region.getName());
			player.sendMessage(
					Chat.BASE_COLOR + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent e) {

		handleMove(e.getPlayer(), e.getTo().toVector());

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onTeleport(PlayerTeleportEvent e) {
		handleMove(e.getPlayer(), e.getTo().toVector());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		onPoint.remove(e.getPlayer().getUniqueId());

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {

		onPoint.remove(e.getEntity().getUniqueId());

	}

}
