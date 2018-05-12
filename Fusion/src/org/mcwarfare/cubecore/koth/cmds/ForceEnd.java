package org.mcwarfare.cubecore.koth.cmds;

import org.mcwarfare.cubecore.koth.gameplay.Point;
import org.mcwarfare.cubecore.koth.gameplay.Points;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
 * 
 * Created on Jul 13, 2016 by Jeremy Gooch.
 * 
 */

public class ForceEnd {

	@Command(name = "forceend", permission = "koth.forcend")
	public void forcestartCommand(CommandArgs args) {

		if (args.length() != 1) {

			Chat.getInstance().messagePlayer(args.getSender(), "&cUsage: /forceend <point>");

			return;
		}

		Point point = Points.get().getPoint(args.getArgs(0));

		if (point == null) {

			Chat.getInstance().messagePlayer(args.getSender(), "&cPoint " + args.getArgs(0) + " does not exist.");

			return;
		}

		point.setActive(false);
		point.reset();

		// broadcastMessage(Cubecore.getInstance().getMessagesConfiguration().getString("messages.koth.started").replaceAll("{koth}",
		// point.getRegion().getName()).replaceAll("{kothLocation}", "x:y:z"));

	}

}
