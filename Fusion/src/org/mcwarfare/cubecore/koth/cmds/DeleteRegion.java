package org.mcwarfare.cubecore.koth.cmds;

import org.mcwarfare.cubecore.koth.gameplay.managers.RegionManager;
import org.mcwarfare.cubecore.koth.utils.region.Region;

import com.mysql.jdbc.Messages;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
 * 
 * Created on Jul 11, 2016 by Jeremy Gooch.
 * 
 */

public class DeleteRegion {

	@Command(name = "deleteregion", permission = "region.delete", usage = "/deleteregion (region)")
	public void deleteRegionCommand(CommandArgs args) {

		if (args.length() != 1) {

			Chat.getInstance().messagePlayer(args.getSender(), "&cUsage: /deleteregion <point>");

			return;
		}

		if (RegionManager.get().getRegion(args.getArgs(0)) == null) {

			Chat.getInstance().messagePlayer(args.getSender(), "&cRegion " + args.getArgs(0) + " was not found.");

			return;
		}

		Region region = RegionManager.get().getRegion(args.getArgs(0));

		RegionManager.get().deleteRegion(region);

		Chat.getInstance().messagePlayer(args.getSender(), Messages.REGION_DELETED);
	}

}
