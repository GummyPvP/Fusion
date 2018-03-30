package fusion.listeners;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.spawn.Spawn;

/**
 * 
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		Player player = e.getPlayer();

		try {
			mKitUser.getInstance(player).load();
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		Utils.giveDefaultItems(player);

		Spawn.getInstance().teleport(player);

		player.getInventory().setArmorContents(null);

		player.getActivePotionEffects().clear();
		
		player.updateInventory();
		
		
		if (mKitUser.getInstance(player).getTeam() == null) 
			return;

		for (Player teammembers : mKitUser.getInstance(player).getTeam().getOnlineMemebers(player)) {

			Utils.sendActionBar(teammembers, ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + " &ajust logged in!"), 20 * 5);

		}

	}

}
