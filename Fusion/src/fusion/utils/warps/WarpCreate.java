package fusion.utils.warps;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.Chat;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.warps.WarpManager;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class WarpCreate {
	
	@Command(name = "setwarp", usage = "/setwarp (name)", description= "Sets a warp", rank = Rank.ADMIN, inGameOnly = true)
	public void warpCreate(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (args.length() != 1) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "/setwarp (name)");
			
			return;
		}
		
		if (player.getItemInHand().getType() == Material.AIR) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "Hold an item for the GUI to use.");
			
			return;
		}
		
		ItemStack item = new ItemBuilder(player.getItemInHand().clone()).name("&d"  + args.getArgs(0)).lore("&aClick to go to the &c" + args.getArgs(0) + " &awarp").build();
		
		if (!WarpManager.getInstance().registerWarp(args.getArgs(0), player.getLocation(), item)) { 
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "That warp already exists.");
			return;
		}
		
		Chat.getInstance().messagePlayer(player, Chat.BASE_COLOR + "Warp " + Chat.IMPORTANT_COLOR + args.getArgs(0) + Chat.BASE_COLOR + " set!");
		
	}

}
