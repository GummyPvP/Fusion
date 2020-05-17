package fusion.event.cmds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fusion.event.util.FusionEvent;
import fusion.kits.utils.Kit;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class EventCommands {
	
	@Command(name = "event", description = "Access event commands", usage = "/event", permission = "fusion.event")
	public void eventCommand(CommandArgs args) {
		
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event start <event name> - starts the event");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event cancel - cancels the event");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event list - list all events");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event join - join the event");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event leave - leave the event");
		
	}
	
	@Command(name = "event.list", description = "List all available events", usage = "/event list", permission = "fusion.event")
	public void eventListCommand(CommandArgs args) {
		
		String eventList = "";
		
		StringBuilder events = new StringBuilder();

		for (FusionEvent event : Fusion.getInstance().getEventHandler().getEvents()) {
			
			if (event == null) continue;
			
			events.append(event.getName()).append(", ");
			
		}
		
		Pattern pattern = Pattern.compile(", $");
		Matcher matcher = pattern.matcher(events.toString());
		eventList = matcher.replaceAll("");
		
		Chat.getInstance().messagePlayer(args.getSender(), "&eList of events: &a" + eventList);
		
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
