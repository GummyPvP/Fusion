package fusion.utils.warps;

import org.bukkit.entity.Player;

import fusion.utils.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class WarpDelete {
	
	@Command(name = "delwarp", usage = "/delwarp (name)", description= "Deletes a warp", rank = Rank.ADMIN, inGameOnly = true)
	public void warpCreate(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (args.length() != 1) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "/delwarp (name)");
			
			return;
		}
		
		if (!WarpManager.getInstance().deleteWarp(args.getArgs(0))) { 
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "That warp doesn't exist!");
			return;
		}
		
		Chat.getInstance().messagePlayer(player, Chat.BASE_COLOR + "Warp " + Chat.IMPORTANT_COLOR + args.getArgs(0) + Chat.BASE_COLOR + " deleted!");
		
	}

}
