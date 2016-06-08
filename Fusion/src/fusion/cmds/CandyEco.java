package fusion.cmds;

import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 5, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class CandyEco {
	
	@Command(name = "candyeco", description = "Manage candy economy.", usage = "/candyeco (args...)", rank = Rank.ADMIN)
	public void candyEco(CommandArgs args) {
		
		if (args.length() < 3) {
			
			// send help
			
			return;
		}
		
		// do stuff
		
	}

}
