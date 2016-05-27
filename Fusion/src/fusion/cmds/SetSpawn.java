package fusion.cmds;

import org.bukkit.entity.Player;

import fusion.utils.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.spawn.Spawn;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class SetSpawn {
	
	@Command(name = "setspawn", aliases = { "spawnset" }, usage = "/setspawn (radius)", description = "Sets the spawnpoint", rank = Rank.ADMIN, inGameOnly = true)
	public void commandSetSpawn(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		Spawn.getInstance().setLocation(player.getLocation());
		
		Chat.getInstance().messagePlayer(player, Chat.STAFF_NOTIFICATION + "Spawnpoint has been set at your location.");
		
	}

}
