package fusion.cmds;

import fusion.main.Fusion;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Created on Jul 10, 2017 by Jeremy Gooch.
	 * 
	 */

public class Help {
	
	@Command(name = "fusion", description = "Displays the help information for Fusion", usage = "/fusion help", permission = "fusion.help")
	public void helpCommand(CommandArgs args) {
		
		Fusion.getInstance().getCommandFramework().messageCommandInformation(args.getSender());
		
	}
	

}
