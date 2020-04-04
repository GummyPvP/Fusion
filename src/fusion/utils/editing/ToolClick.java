package fusion.utils.editing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fusion.utils.chat.Chat;
import fusion.utils.editing.event.PlayerSelectPointEvent;

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
		
		if (item == null) return;
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR) return;
		
		if (EditorManager.getInstance().getEditor(item.getType()) == null) return;
		
		Editor editor = EditorManager.getInstance().getEditor(item.getType());
		
		if (!player.hasPermission("region.wand." + editor.getName())) return;
		
		if (editor.getTool() == item.getType()) {
			
			if (EditorSessions.getInstance().getSession(player, editor) == null) {
				
				EditorSessions.getInstance().addSession(player, editor.clone());
				
				Chat.getInstance().messagePlayer(player, Chat.STAFF_NOTIFICATION + "Changed your editor to " + editor.getName() + ".");
				
			}
			
			Bukkit.getPluginManager().callEvent(new PlayerSelectPointEvent(EditorSessions.getInstance().getSession(player, editor), e.getClickedBlock().getLocation().toVector(), e.getAction()));
			
			e.setCancelled(true);
			
		}
		
	}

}
