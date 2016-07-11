package fusion.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fusion.kits.utils.Kit;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;

/**
	 * 
	 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerDeath implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		
		Player player = e.getEntity();
		mKitUser user = mKitUser.getInstance(player);
		
		CombatLog.getInstance().remove(player);
		
		player.getWorld().strikeLightningEffect(player.getLocation());
		
		e.getDrops().clear();
		
		// make it so they don't drop anything, but the killer gets their own specialized healing item (depending on their /settings)
		
		Kit oldKit = user.getKit();
		
		user.setPreviousKit(oldKit);
		
		user.setKit(null);
		
		if (!(player.getKiller() instanceof Player)) {
			
			e.setDeathMessage(Chat.IMPORTANT_COLOR + player.getName() + Chat.BASE_COLOR + " died.");
			
			return;
		}
		
		mKitUser killer = mKitUser.getInstance(player.getKiller());
		
		double playerCandies = user.getCandies();
		
		double rewardAmount = playerCandies * .25; // 25% of whatever their balance is
		
		rewardAmount = Math.round(rewardAmount);
		
		rewardAmount = rewardAmount <= 10.0 ? 10.0 : rewardAmount; // ensuring that the minimum is 10.0 candies
		
		killer.addCandies(rewardAmount);
		
		Chat.getInstance().messagePlayer(player.getKiller(), Chat.SECONDARY_BASE + "You received " + Chat.IMPORTANT_COLOR + rewardAmount + Chat.SECONDARY_BASE + " candies for killing " + player.getName());
		
		e.setDeathMessage(Chat.SECONDARY_BASE + player.getName() + Chat.IMPORTANT_COLOR + " (" + (oldKit == null ? "Nothing" : oldKit.getName()) + ") "
		+ Chat.BASE_COLOR + "was slain by "
		+ Chat.SECONDARY_BASE + player.getKiller().getName() + Chat.IMPORTANT_COLOR +
		" (" + (killer.hasKit() ? killer.getKit().getName() : "Nothing") + ")");
		
	}

}
