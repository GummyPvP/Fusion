package fusion.cmds;

import org.bukkit.entity.Player;

import fusion.kits.utils.KitManager;
import fusion.utils.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.gui.KitGUI;

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
		
		if (!(args.length() >= 1)) {
			
			new KitGUI(player);
			
			return;
			
		}
		
		String wantedKit = args.getArgs(0);
		
		if (KitManager.getInstance().valueOf(wantedKit) == null) {
			
			Chat.getInstance().messagePlayer(player, "Could not find the kit you wanted, opening KitGUI.");
			
			new KitGUI(player);
			
			return;
		}
		
		KitManager.getInstance().valueOf(wantedKit).apply(player);
		
	}

}
