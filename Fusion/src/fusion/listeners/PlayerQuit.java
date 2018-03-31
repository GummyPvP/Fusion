package fusion.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fusion.main.Fusion;
import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		Player player = e.getPlayer();
		
		if (player.hasMetadata("noFall")) {
			
			player.removeMetadata("noFall", Fusion.getInstance());
			
		}
		
		mKitUser.getInstance(player).save();
		
		mKitUser.getInstance(player).unload();
		
		if (CombatLog.getInstance().isInCombat(player)) {
			
			player.setHealth(0.0);
			
			Bukkit.broadcastMessage(Chat.IMPORTANT_COLOR + player.getName() + Chat.BASE_COLOR + " logged out during a fight!");
			
			CombatLog.getInstance().remove(player);
			
		}
		
		if (mKitUser.getInstance(player).getTeam() != null) {

			for (Player teammembers : mKitUser.getInstance(player).getTeam().getOnlineMemebers(player)) {

					Utils.sendActionBar(teammembers, ChatColor.translateAlternateColorCodes('&',
							player.getDisplayName() + " &cjust logged out!"), 20 * 5);


			}

		}
		
	}

}
