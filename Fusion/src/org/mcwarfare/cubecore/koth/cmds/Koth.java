
package org.mcwarfare.cubecore.koth.cmds;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Koth {
	
	@Command(name = "koth", permission = "koth.koth", usage = "/koth")
	public void onKothCommand(CommandArgs args) {
		
		Chat.getInstance().messagePlayer(args.getSender(), "&c/createpoint <name> <day> <time> &e- Create a koth point");
		Chat.getInstance().messagePlayer(args.getSender(), "&c/deletepoint <point> &e- Delete a point");
		Chat.getInstance().messagePlayer(args.getSender(), "&c/forcestart <point> &e- Force start a koth");
		Chat.getInstance().messagePlayer(args.getSender(), "&c/forceend <name> &e- Force end a koth");
		
		Chat.getInstance().messagePlayer(args.getSender(), "&c/createregion <name> &e- Create a koth region");
		Chat.getInstance().messagePlayer(args.getSender(), "&c/deleteregion <name> &e- Delete a koth region");
	}

}
