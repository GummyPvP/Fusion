package fusion.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fusion.utils.ConfigManager;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Balance {
	
	@Command(name = "balance", aliases = { "bal", "candies", "money", "coins", "candy" }, description = "Shows your candy balance.", usage = "/balance")
	public void balanceCommand(CommandArgs args) {
		
		if (args.length() != 1) {
			
			if (!(args.getSender() instanceof Player)) {
				
				Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "You aren't a player!");
				
				return;
			}
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.SECONDARY_BASE + "Candies: " + Chat.IMPORTANT_COLOR + mKitUser.getInstance(args.getPlayer()).getCandies());
			
			return;
		}
		
		Player target = Bukkit.getPlayer(args.getArgs(0));
		
		if (target == null) {
			
			String check = args.getArgs(0);
			
			if (ConfigManager.getPlayerFile(check).getDouble("profile.candies") != null) {
				
				Chat.getInstance().messagePlayer(args.getSender(), Chat.SECONDARY_BASE + "Candy amount of " + check + ": " + Chat.IMPORTANT_COLOR + ConfigManager.getPlayerFile(check).getDouble("profile.candies"));
				return;
			}
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "Couldn't find profile of " + check + "!");
			
			return;
		}
		
		Chat.getInstance().messagePlayer(args.getSender(), Chat.SECONDARY_BASE + "Candy amount of " + target.getName() + ": " + Chat.IMPORTANT_COLOR + mKitUser.getInstance(target).getCandies());
		
	}

}
