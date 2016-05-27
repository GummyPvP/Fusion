package fusion.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fusion.kits.utils.Kit;
import fusion.utils.Chat;
import fusion.utils.ItemBuilder;
import fusion.utils.mKitUser;

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
		
		e.getDrops().clear();
		
		for (int i = 0; i < 10; i++) {
			
			e.getDrops().add(new ItemBuilder(Material.MUSHROOM_SOUP).name("&bSoup").lore("Drinking this soup heals you 3.5 hearts").build());
			
		}
		
		Kit oldKit = user.getKit();
		
		user.setPreviousKit(oldKit);
		
		user.setKit(null);
		
		if (!(player.getKiller() instanceof Player)) {
			
			e.setDeathMessage(Chat.IMPORTANT_COLOR + player.getName() + Chat.BASE_COLOR + " died.");
			
			return;
		}
		
		mKitUser killer = mKitUser.getInstance(player.getKiller());
		
		e.setDeathMessage(Chat.IMPORTANT_COLOR + player.getName() + " (" + (oldKit == null ? "Nothing" : oldKit.getName()) + ") "
		+ Chat.BASE_COLOR + "was slain by "
		+ Chat.IMPORTANT_COLOR + player.getKiller().getName() + 
		" (" + (killer.hasKit() ? killer.getKit().getName() : "Nothing") + ")");
		
	}

}
