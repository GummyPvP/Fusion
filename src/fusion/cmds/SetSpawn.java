package fusion.cmds;

import org.bukkit.entity.Player;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.spawn.Spawn;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class SetSpawn {
	
	@Command(name = "setspawn", aliases = { "spawnset" }, usage = "/setspawn", description = "Sets the spawnpoint", permission = "spawn.set", inGameOnly = true)
	public void commandSetSpawn(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		Spawn.getInstance().setLocation(player.getLocation());
		
		Chat.getInstance().messagePlayer(player, Chat.STAFF_NOTIFICATION + "Spawnpoint has been set at your location.");
		
	}

}
