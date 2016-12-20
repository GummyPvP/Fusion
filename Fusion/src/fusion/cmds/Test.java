package fusion.cmds;

import org.bukkit.Bukkit;

import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.crates.CrateManager;
import mpermissions.utils.permissions.Rank;

/**
 * 
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Test {

	@SuppressWarnings("deprecation")
	@Command(name = "test", rank = Rank.ADMINPLUS, description = "Test command", usage = "/test", inGameOnly = true)
	public void testCommand(CommandArgs args) {

		// TextUtils.MakeText("Jonhan is gay", args.getPlayer().getLocation(),
		// BlockFace.NORTH, Material.BEDROCK.getId(), (byte) 0,
		// TextAlign.CENTER);
		
		Bukkit.broadcastMessage("Test has executed");

		if (args.length() == 1) {

			CrateManager.getInstance().getCrate(args.getArgs(0)).apply(args.getPlayer());

		}

	}

}
