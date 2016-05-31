package fusion.utils.warps;

import org.bukkit.entity.Player;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.gui.WarpGUI;
import fusion.utils.warps.WarpManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class WarpList {
	
	@Command(name = "warp", usage = "/warp", description= "Shows list of warps", aliases = { "warps" }, inGameOnly = true)
	public void warpList(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (args.length() < 1) {
			
			new WarpGUI(player);
			
			return;
		}
		
		if (WarpManager.getInstance().getWarp(args.getArgs(0)) == null) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "Warp '" + args.getArgs(0) + "' does not exist!");
			
			return;
		}
		
		WarpManager.getInstance().sendPlayer(player, WarpManager.getInstance().getWarp(args.getArgs(0)));
		
	}

}
