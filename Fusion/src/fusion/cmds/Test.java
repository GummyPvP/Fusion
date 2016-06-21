package fusion.cmds;

import org.bukkit.Bukkit;

import fusion.listeners.CombatLog;
import fusion.utils.mKitUser;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Test {
	
	@Command(name = "test", rank=Rank.ADMINPLUS, description = "Test command", usage = "/test", inGameOnly = true)
	public void testCommand(CommandArgs args) {
		
		mKitUser.getInstance(args.getPlayer()).addCandies(500);
		
		CombatLog.getInstance().debug();
		
		Bukkit.broadcastMessage(Bukkit.getScheduler().getPendingTasks().toString());
		
	}

}
