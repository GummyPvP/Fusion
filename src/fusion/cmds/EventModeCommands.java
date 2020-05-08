package fusion.cmds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class EventModeCommands {
	
	@Command(name = "eventmode", aliases = { "event" }, description = "Allows admins to configure event mode", usage = "/eventmode <args>", permission = "fusion.eventmode")
	public void eventModeCommand(CommandArgs args) {
		
		String kitList = "";
		
		StringBuilder kits = new StringBuilder();

		for (Kit kit : Fusion.getInstance().getEventModeHandler().getAllowedKits()) {
			
			if (kit == null) continue;
			
			kits.append(kit.getName()).append(", ");
			
		}
		
		Pattern pattern = Pattern.compile(", $");
		Matcher matcher = pattern.matcher(kits.toString());
		kitList = matcher.replaceAll("");
		
		long timeLastStarted = Fusion.getInstance().getEventModeHandler().getTimeLastStarted();
		
		Chat.getInstance().messagePlayer(args.getSender(), "&e--== Event Mode ==--");
		Chat.getInstance().messagePlayer(args.getSender(), "&eEvent mode: " + (Fusion.getInstance().getEventModeHandler().isInEventMode() ? "&a&lON" : "&c&lOFF"));
		Chat.getInstance().messagePlayer(args.getSender(), "&eAllowed kits: &a" + kitList);
		Chat.getInstance().messagePlayer(args.getSender(), "&eTime running: &6" + (timeLastStarted == 0L ? "N/A" : convertTimeToString(System.currentTimeMillis() - timeLastStarted)));
	}
	
	@Command(name = "eventmode.toggle", aliases = { "event.toggle" }, description = "Allows admins to toggle event mode", usage = "/eventmode toggle", permission = "fusion.eventmode")
	public void eventModeToggle(CommandArgs args) {
		
		Fusion.getInstance().getEventModeHandler().setInEventMode(!Fusion.getInstance().getEventModeHandler().isInEventMode());
		Chat.getInstance().messagePlayer(args.getSender(), "&eEvent mode is now GLOBALLY turned " + (Fusion.getInstance().getEventModeHandler().isInEventMode() ? "&a&lON" : "&c&lOFF"));
		
	}
	
	@Command(name = "eventmode.addkit", aliases = { "event.addkit" }, description = "Allows admins to allow a kit in event mode", usage = "/eventmode addkit <kit>", permission = "fusion.eventmode")
	public void eventmodeKitAdd(CommandArgs args) {
		
		if (args.length() == 0) return;
		
		if (args.getArgs(0).equalsIgnoreCase("all")) {
			
			for (Kit kit : KitManager.getInstance().getKits()) {
				Fusion.getInstance().getEventModeHandler().addAllowedKit(kit);
			}
			
			Chat.getInstance().messagePlayer(args.getSender(), "&eAll kits are now allowed during event mode");
			
			return;
			
		}
		
		Kit kit = KitManager.getInstance().valueOf(args.getArgs(0));
		
		if (kit == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&cKit &e" + args.getArgs(0) + " &cdoes not exist");
			return;
		}
		
		Fusion.getInstance().getEventModeHandler().addAllowedKit(kit);
		Chat.getInstance().messagePlayer(args.getSender(), "&eKit &a" + kit.getName() + " &eis now allowed during event mode");
		
	}
	
	@Command(name = "eventmode.removekit", aliases = { "event.removekit" }, description = "Allows admins to block a kit in event mode", usage = "/eventmode removekit <kit>", permission = "fusion.eventmode")
	public void eventmodeKitRemove(CommandArgs args) {
		
		if (args.length() == 0) return;
		
		if (args.getArgs(0).equalsIgnoreCase("all")) {
			
			for (Kit kit : KitManager.getInstance().getKits()) {
				Fusion.getInstance().getEventModeHandler().removeAllowedKit(kit);
			}
			
			Chat.getInstance().messagePlayer(args.getSender(), "&eAll kits are now blocked during event mode");
			
			return;
			
		}
		
		Kit kit = KitManager.getInstance().valueOf(args.getArgs(0));
		
		if (kit == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&cKit &e" + args.getArgs(0) + " &cdoes not exist");
			return;
		}
		
		Fusion.getInstance().getEventModeHandler().removeAllowedKit(kit);
		Chat.getInstance().messagePlayer(args.getSender(), "&eKit &a" + kit.getName() + " &eis now blocked during event mode");
		
	}
	
	private String convertTimeToString(long durationInMillis) {
		
		long millis = durationInMillis % 1000;
		long second = (durationInMillis / 1000) % 60;
		long minute = (durationInMillis / (1000 * 60)) % 60;
		long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d", hour, minute, second);
		
		return time;
	}
	
}
