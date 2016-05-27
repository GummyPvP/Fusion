package fusion.cmds;

import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.gui.WarpGUI;
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
		
		new WarpGUI(args.getPlayer());
		
	}

}
