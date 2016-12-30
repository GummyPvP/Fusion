package fusion.events.cmds;

import org.bukkit.entity.Player;

import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.gui.EventJoinGUI;

/**
	 * 
	 * Created on Dec 28, 2016 by Jeremy Gooch.
	 * 
	 */

public class EventJoin {
	
	// TODO: Make a more efficient and automatic method
	
	@Command(name = "event.join", description = "Allows you to join an Event", usage = "/event join", inGameOnly = true)
	public void eventJoinCommand(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		player.openInventory(EventJoinGUI.get().getInventory());
		
	}
}
