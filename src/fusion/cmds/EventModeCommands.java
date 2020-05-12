package fusion.cmds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class EventModeCommands {
	
	@Command(name = "eventmode", description = "Allows admins to configure event mode", usage = "/eventmode <args>", permission = "fusion.eventmode")
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
		Chat.getInstance().messagePlayer(args.getSender(), "&ePVP: " + (Fusion.getInstance().getEventModeHandler().isPVPEnabled() ? "&a&lON" : "&c&lOFF"));
		Chat.getInstance().messagePlayer(args.getSender(), "&eAllowed kits: &a" + kitList);
		Chat.getInstance().messagePlayer(args.getSender(), "&eTime running: &6" + (timeLastStarted == 0L ? "N/A" : convertTimeToString(System.currentTimeMillis() - timeLastStarted)));
	}
	
	@Command(name = "eventmode.toggle", description = "Allows admins to toggle event mode", usage = "/eventmode toggle", permission = "fusion.eventmode")
	public void eventModeToggle(CommandArgs args) {
		
		Fusion.getInstance().getEventModeHandler().setInEventMode(!Fusion.getInstance().getEventModeHandler().isInEventMode());
		Chat.getInstance().messagePlayer(args.getSender(), "&eEvent mode is now GLOBALLY turned " + (Fusion.getInstance().getEventModeHandler().isInEventMode() ? "&a&lON" : "&c&lOFF"));
		Chat.getInstance().broadcastMessage("&e&lEvent mode has been toggled " + (Fusion.getInstance().getEventModeHandler().isInEventMode() ? "&a&lON" : "&c&lOFF") + "&e&l!");
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 1.0f, 1.0f);
		}
		
	}
	
	@Command(name = "eventmode.togglepvp", description = "Allows admins to toggle pvp globally", usage = "/eventmode togglepvp", permission = "fusion.eventmode")
	public void eventModeTogglePVP(CommandArgs args) {
		
		Fusion.getInstance().getEventModeHandler().setPVPEnabled(!Fusion.getInstance().getEventModeHandler().isPVPEnabled());
		Chat.getInstance().messagePlayer(args.getSender(), "&ePVP is now GLOBALLY turned " + (Fusion.getInstance().getEventModeHandler().isPVPEnabled() ? "&a&lON" : "&c&lOFF"));
		Chat.getInstance().broadcastMessage("&e&lPVP has been toggled " + (Fusion.getInstance().getEventModeHandler().isPVPEnabled() ? "&a&lON" : "&c&lOFF") + "&e&l!");
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0f, 1.0f);
		}
		
	}
	
	@Command(name = "eventmode.addkit", description = "Allows admins to allow a kit in event mode", usage = "/eventmode addkit <kit>", permission = "fusion.eventmode")
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
	
	@Command(name = "eventmode.removekit", description = "Allows admins to block a kit in event mode", usage = "/eventmode removekit <kit>", permission = "fusion.eventmode")
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
	
	@Command(name = "eventmode.createkit", description = "Allows admins to create a custom kit in event mode", usage = "/eventmode createkit <kit>", permission = "fusion.eventmode")
	public void eventmodeKitCreate(CommandArgs args) {
		
		if (args.length() == 0) return;
		
		if (Fusion.getInstance().getEventModeHandler().getCustomKit(args.getArgs(0)) != null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&eKit &a" + args.getArgs(0) + " &ealready exists");
			return;
		}
		
		Fusion.getInstance().getEventModeHandler().addCustomKit(args.getArgs(0), args.getPlayer().getInventory().getContents());
		
		Chat.getInstance().messagePlayer(args.getSender(), "&eKit &a" + args.getArgs(0) + " &ehas been added to the event mode kit list with your inventory contents");
		
	}
	
	@Command(name = "eventmode.deletekit", description = "Allows admins to delete a custom kit in event mode", usage = "/eventmode deletekit <kit>", permission = "fusion.eventmode")
	public void eventmodeKitDelete(CommandArgs args) {
		
		if (args.length() == 0) return;
		
		if (Fusion.getInstance().getEventModeHandler().getCustomKit(args.getArgs(0)) == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&eKit &a" + args.getArgs(0) + " &edoes not exist");
			return;
		}
		
		Fusion.getInstance().getEventModeHandler().removeCustomKit(args.getArgs(0));
		
		Chat.getInstance().messagePlayer(args.getSender(), "&eKit &a" + args.getArgs(0) + " &ehas been removed from the event mode kit list");
		
	}
	
	@Command(name = "eventmode.applykit", description = "Allows admins to apply a custom kit in event mode", usage = "/eventmode applykit <kit> <user>", permission = "fusion.eventmode")
	public void eventmodeKitApply(CommandArgs args) {
		
		if (args.length() < 2) return;
		
		if (Fusion.getInstance().getEventModeHandler().getCustomKit(args.getArgs(0)) == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&eKit &a" + args.getArgs(0) + " &edoes not exist");
			return;
		}
		
		if (args.getArgs(1).equalsIgnoreCase("all")) {
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				Bukkit.dispatchCommand(args.getSender(), "eventmode applykit " + args.getArgs(0) + " " + player.getName());
			}
			
			return;
		}
		
		Player target = Bukkit.getPlayer(args.getArgs(1));
		
		if (target == null) {
			Chat.getInstance().messagePlayer(args.getSender(), "&ePlayer &c" + args.getArgs(1) + " &edoes not exist");
			return;
		}
		
		Fusion.getInstance().getEventModeHandler().applyCustomKit(target, args.getArgs(0));
		
		Chat.getInstance().messagePlayer(args.getSender(), "&eKit &a" + args.getArgs(0) + " &ehas been applied to " + target.getName());
		Chat.getInstance().messagePlayer(target, "&eKit &a" + args.getArgs(0) + " &ehas been applied to you");
		
	}
	
	@Command(name = "eventmode.listkits", aliases = { "eventmode.kitlist" }, description = "Allows admins to list all custom kits in event mode", usage = "/eventmode listkits", permission = "fusion.eventmode")
	public void eventmodeKitList(CommandArgs args) {
		
		String kitList = "";
		
		for (String name : Fusion.getInstance().getEventModeHandler().getCustomKits().keySet()) {
			kitList += "&a" + name + "&7, ";
		}
		
		Pattern pattern = Pattern.compile(", $");
		Matcher matcher = pattern.matcher(kitList.toString());
		kitList = matcher.replaceAll("");
		
		Chat.getInstance().messagePlayer(args.getSender(), "&eCustom event kits: &a" + (kitList.equalsIgnoreCase("") ? "none" : kitList));
		
	}
	
	@Command(name = "eventmode.help", description = "Event mode help menu", usage = "/eventmode help", permission = "fusion.eventmode")
	public void eventmodeKitHelp(CommandArgs args) {
		
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event toggle - Toggles server wide event mode");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event addkit <kit name> - Adds a kit to the allowed event mode kits");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event removekit <kit name> - Removes a kit from the allowed event mode kits");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event createkit <kit name> - Creates a custom kit based on your inventory contents");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event deletekit <kit name> - Removes a custom kit");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event listkits - Displays all custom kits");
		Chat.getInstance().messagePlayer(args.getSender(), "&e/event applykit <kit> <user or 'all'> - Applies a custom kit to a player or all");
		
	}
	
	private String convertTimeToString(long durationInMillis) {
		
		long second = (durationInMillis / 1000) % 60;
		long minute = (durationInMillis / (1000 * 60)) % 60;
		long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d", hour, minute, second);
		
		return time;
	}
	
}
