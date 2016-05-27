package fusion.utils.editing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fusion.utils.editing.event.PlayerSelectPointEvent;
import klap.utils.mPlayer;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class ToolClick implements Listener {
	
	@EventHandler
	public void onToolClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		
		if (EditorManager.getInstance().getEditor(item.getType()) == null) return;
		
		Editor editor = EditorManager.getInstance().getEditor(item.getType());
		
		if (!mPlayer.getInstance(player).getGroup().getRank().hasRequiredRank(Rank.ADMIN)) return;
		
		e.setCancelled(true);
		
		if (EditorSessions.getInstance().getSession(player) == null) {
			
			EditorSessions.getInstance().addSession(player, editor);
			
		}
		
		Bukkit.getPluginManager().callEvent(new PlayerSelectPointEvent(player, EditorSessions.getInstance().getSession(player), e.getAction()));
		
	}

}
