package fusion.cmds;

import fusion.listeners.CombatLog;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
 * 
 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class CombatLogCommand {

	@Command(name = "combatlog", aliases = { "combattag", "ct" }, description = "Tells you how much time you have left before you can log out.", usage = "/combatlog", inGameOnly = true)
	public void combatLog(CommandArgs args) {
		
		if (CombatLog.getInstance().isInCombat(args.getPlayer())) {
			
			int timeLeft = CombatLog.getInstance().getRemainingTime(args.getPlayer());
			
			Chat.getInstance().messagePlayer(args.getPlayer(), Chat.BASE_COLOR + "You are in combat for " + Chat.IMPORTANT_COLOR + timeLeft + Chat.BASE_COLOR + " more " + (timeLeft == 1 ? "second" : "seconds") +  "!");
			
		} else Chat.getInstance().messagePlayer(args.getPlayer(), Chat.SECONDARY_BASE + "You are not in combat!");
		
	}

}
