package fusion.cmds;

import org.bukkit.block.BlockFace;

import fusion.utils.TextUtils;
import fusion.utils.TextUtils.TextAlign;
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
		
		TextUtils.MakeText("Elijah Burgess", args.getPlayer().getLocation(), BlockFace.NORTH, 35, (byte) 9, TextAlign.CENTER);
		
	}

}
