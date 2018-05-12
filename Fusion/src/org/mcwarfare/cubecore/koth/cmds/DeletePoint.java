package org.mcwarfare.cubecore.koth.cmds;

import org.mcwarfare.cubecore.koth.gameplay.Points;
import org.mcwarfare.cubecore.koth.gameplay.managers.PointRegionManager;

import com.mysql.jdbc.Messages;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
 * 
 * Created on Jul 12, 2016 by Jeremy Gooch.
 * 
 */

public class DeletePoint {

	@Command(name = "deletepoint", permission = "koth.point.delete", inGameOnly = true)
	public void createPointCommand(CommandArgs args) {
		
		if (args.length() != 1) {
			
			Chat.getInstance().messagePlayer(args.getSender(), "&cUsage: /deletepoint <point>");
			
			return;
		}
		
		if (Points.get().getPoint(args.getArgs(0)) == null) {
			
			Chat.getInstance().messagePlayer(args.getSender(), "&cPoint " + args.getArgs(0) + " was not found.");
			
			return;
		}

		PointRegionManager.get().deleteRegion(Points.get().getPoint(args.getArgs(0)).getRegion());
		Points.get().deletePoint((Points.get().getPoint(args.getArgs(0))));
		Chat.getInstance().messagePlayer(args.getSender(), Messages.POINT_DELETED);
		
	}
}
