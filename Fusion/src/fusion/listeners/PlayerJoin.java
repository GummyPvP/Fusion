package fusion.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import fusion.utils.StatsManager;
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

		final Player player = e.getPlayer();

		mKitUser.getInstance(player).load();

		Utils.giveDefaultItems(player);

		Spawn.getInstance().teleport(player);

		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));

		player.getActivePotionEffects().clear();

		player.updateInventory();

		StatsManager.getInstance().refreshScoreBoard(player, false);

		if (mKitUser.getInstance(player).getTeam() != null) {

			for (Player teammembers : mKitUser.getInstance(player).getTeam().getOnlineMemebers(player)) {


					Utils.sendActionBar(teammembers, ChatColor.translateAlternateColorCodes('&',
							player.getDisplayName() + " &ajust logged in!"), 20 * 5);

				
			}

		}

	}

}
