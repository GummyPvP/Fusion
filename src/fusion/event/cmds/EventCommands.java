package fusion.event.cmds;

import fusion.event.util.FusionEvent;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class EventCommands {
	
	@Command(name = "event", description = "Access event commands", usage = "/event", permission = "fusion.event")
	public void eventCommand(CommandArgs args) {
		
	}
	
	@Command(name = "event.start", description = "Start an automated event", usage = "/event start <event name>", permission = "fusion.event")
	public void startEventCommand(CommandArgs args) {
		
		if (Fusion.getInstance().getEventHandler().getCurrentEvent() != null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&cThere is already an event running right now!");
			return;
		}
		
		FusionEvent event = Fusion.getInstance().getEventHandler().getEvent(args.getArgs(0));
		
		if (event == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&c" + args.getArgs(0) + " could not be located as a valid event");
			return;
		}
		
		Fusion.getInstance().getEventHandler().runEvent(event);
		
	}

	@Command(name = "event.join", description = "Join the current event", usage = "/event join", inGameOnly = true)
	public void eventJoinCommand(CommandArgs args) {
		
		if (Fusion.getInstance().getEventHandler().getCurrentEvent() == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&cThere is currently no event running!");
			return;
		}
		
		Fusion.getInstance().getEventHandler().getCurrentEvent().addPlayer(args.getPlayer());
		
	}
	
	@Command(name = "event.leave", description = "Leave the current event", usage = "/event leave", inGameOnly = true)
	public void eventLeaveCommand(CommandArgs args) {
		
		if (Fusion.getInstance().getEventHandler().getCurrentEvent() == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&cThere is currently no event running!");
			return;
		}
		
		Fusion.getInstance().getEventHandler().getCurrentEvent().removePlayer(args.getPlayer());
		
	}
	
	@Command(name = "event.cancel", description = "Cancel the current event", usage = "/event cancel", permission = "fusion.event")
	public void eventCancelCommand(CommandArgs args) {
		
		FusionEvent event = Fusion.getInstance().getEventHandler().getCurrentEvent();
		
		if (event == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&cThere is currently no event running!");
			return;
		}
		
		if (!event.isStarted()) {
			Fusion.getInstance().getEventHandler().getAnnouncementTask().cancel();
		}
		
		event.end();
		
		Chat.getInstance().broadcastMessage("&7[&eEvent&7] &3" + event.getName() + " &ahas been cancelled by an administrator!");
		
	}
	
}
