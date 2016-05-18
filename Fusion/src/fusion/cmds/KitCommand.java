package fusion.cmds;

import org.bukkit.entity.Player;

import fusion.utils.KitGUI;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Copyright GummyPvP. Created on May 17, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class KitCommand {
	
	@Command(name = "kit", aliases = { "kits", "kitgui" }, usage = "/kit", description = "Shows available kits.", inGameOnly = true)
	public void commandKit(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		new KitGUI(player);
		
		
	}

}
