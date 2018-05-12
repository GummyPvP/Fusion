package org.mcwarfare.cubecore.koth.gameplay;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.mcwarfare.cubecore.koth.utils.regions.PointRegion;

/**
 * 
 * Created on Jul 10, 2016 by Jeremy Gooch.
 * 
 */

public class PointTracker implements Listener {

	private PointRegion region;

	private LinkedHashSet<String> playersInRegion = new LinkedHashSet<String>();

	public PointTracker(PointRegion region) {

		this.region = region;

	}

	public Set<String> getPlayersOnPoint() {

		return playersInRegion;

	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		check(e.getPlayer(), e.getTo());

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		playersInRegion.remove(e.getPlayer().getName());

	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {

		check(e.getPlayer(), e.getTo());

	}


	private void check(Player player, Location location) {

		if (region.getBounds().isInBounds(location)) {

			if (playersInRegion.contains(player.getName()))
				return;

			playersInRegion.add(player.getName());

		} else
			playersInRegion.remove(player.getName());

	}

}
