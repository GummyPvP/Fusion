package fusion.cmds;

import org.bukkit.entity.Player;

import fusion.listeners.CombatLog;
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

public class SpawnCommand {
	
	@Command(name = "spawn", description = "Teleports you to the spawnpoint.", usage = "/spawn", inGameOnly = true)
	public void spawnCommand(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (CombatLog.getInstance().isInCombat(args.getPlayer())) {

			Chat.getInstance().messagePlayer(args.getPlayer(),
					Chat.IMPORTANT_COLOR + "You are in combat! You may not use this command.");

			return;
		}
		
		Spawn.getInstance().teleport(player);
		
	}

}
