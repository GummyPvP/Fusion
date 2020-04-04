package fusion.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fusion.kits.utils.Kit;
import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;

/**
 * 
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class PlayerDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {

		Player player = e.getEntity();
		mKitUser user = mKitUser.getInstance(player);

		if (player.hasMetadata("noFall")) {

			player.removeMetadata("noFall", Fusion.getInstance());

		}

		CombatLog.getInstance().remove(player);

		player.getWorld().strikeLightningEffect(player.getLocation());

		e.getDrops().clear();

		// make it so they don't drop anything, but the killer gets their own
		// specialized healing item (depending on their /settings)

		Kit oldKit = user.getKit();

		user.setPreviousKit(oldKit);

		user.setKit(null);

		if (!(player.getKiller() instanceof Player)) {

			e.setDeathMessage(Chat.IMPORTANT_COLOR + player.getName() + Chat.BASE_COLOR + " died.");

			return;
		}

		mKitUser killer = mKitUser.getInstance(player.getKiller());

		double rewardAmount = 15.0;

		killer.addKillStreak();
		killer.addKill();
		user.addDeath();
		
		if (killer.getKillStreak() != 0) {
			rewardAmount *= (killer.getKillStreak() / 100);
		}
		
		killer.addCandies(rewardAmount); // 5 killstreak = 5% extra reward

		int killerKS = killer.getKillStreak();
		int ks = killerKS % 5;

		if (ks == 0) {

			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Chat.CHAT_PREFIX + "&aWOAH! &b"
					+ killer.getPlayer().getName() + " &ais on a killstreak of &e" + killerKS + "&a!"));

		}
		
		int userKS = user.getKillStreak();

		if ((userKS >= 5) && (userKS != 0)) {

			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							Chat.CHAT_PREFIX + "&aWOAH! &b" + user.getPlayer().getName() + " &alost their killstreak of &e" + userKS + " &ato &b" + killer.getPlayer().getName() + "&a!"));

		}
		
		user.resetKillStreak();
		
		rewardAmount = Math.round(rewardAmount);
		
		rewardAmount = rewardAmount <= 10.0 ? 10.0 : rewardAmount; // making sure that the minimum is 10 candies
		
		killer.addCandies(rewardAmount);
//		user.removeCandies(rewardAmount);
//		
//		
//		Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You lost " + Chat.IMPORTANT_COLOR + rewardAmount + Chat.SECONDARY_BASE + " candies for getting killed by " +  player.getKiller().getName());

		e.setDeathMessage(Chat.SECONDARY_BASE + player.getName() + Chat.IMPORTANT_COLOR + " ("
				+ (oldKit == null ? "Nothing" : oldKit.getName()) + ") " + Chat.BASE_COLOR + "was slain by " 
				+ Chat.SECONDARY_BASE + player.getKiller().getName() + Chat.IMPORTANT_COLOR + " (" + (killer.hasKit() ? killer.getKit().getName() : "Nothing") + ")");

	}

}
