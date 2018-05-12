package org.mcwarfare.cubecore.koth.cmds;

import org.mcwarfare.cubecore.koth.gameplay.Point;
import org.mcwarfare.cubecore.koth.gameplay.Points;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Created on Jul 14, 2016 by Jeremy Gooch.
	 * 
	 */

public class EditPoint {
	
	@Command(name = "editpoint", permission = "koth.editpoint")
	public void editPointCommand(CommandArgs args) {
		
		if (args.length() <= 1) {
			
			Chat.getInstance().messagePlayer(args.getSender(), "&cUsage: /editpoint <point> (flag) (modifier)");
			
			return;
		}
		
		if (args.length() == 2) {
			
			Point point = Points.get().getPoint(args.getArgs(0));
			String flag = args.getArgs(1);
			
			if (point == null) {
				
				Chat.getInstance().messagePlayer(args.getSender(), "&cPoint " + args.getArgs(0) + " does not exist!");
				
				return;
			}
			
			if (!checkFlag(flag)) {
				
				Chat.getInstance().messagePlayer(args.getSender(), "&cNot correct flag! Flags: capturetime");
				
				return;
			}
			
			if (flag.equalsIgnoreCase("capturetime")) {
				
				
				
			}
			
		}
		
	}
	
	private boolean checkFlag(String flag) {
		
		if (flag.equalsIgnoreCase("capturetime")) return true;
		
		return false;
		
	}

}
