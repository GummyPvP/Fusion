package fusion.cmds;

import fusion.events.lms.LMS;
import fusion.events.lms.LMSState;
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
		
		LMS.getInstance().start(args.getPlayer().getName());
		
		LMS.getInstance().setState(LMSState.GRACE);
		
	}

}
