package fusion.events.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import klap.utils.mPlayer;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Created on Dec 25, 2016 by Jeremy Gooch.
	 * 
	 */

public class EventCommand {
	
	@Command(name = "event", description = "Base command to manage events", usage = "/event (args)")
	public void eventCommand(CommandArgs args) {
		
		CommandSender sender = args.getSender();
		
		if (!(sender instanceof Player)) {
			
			sendInformation(sender, Rank.ADMINPLUS);
			
			return;
		}
		
		sendInformation(sender, mPlayer.getInstance(((Player) sender)).getGroup().getRank());
		
	}
	
	private void sendInformation(CommandSender sender, Rank rank) {
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m-----&r &5Event Commands &7&m-----"));
		
		Chat.getInstance().messagePlayer(sender, "&e/event join - Brings up the Event GUI");
		
		if (rank.hasRequiredRank(Rank.SLIME)) {
			
			Chat.getInstance().messagePlayer(sender, "&e/event host - Brings up the Event Hosting GUI");
			
		}
		
		if (rank.hasRequiredRank(Rank.MODERATOR)) {
			
			Chat.getInstance().messagePlayer(sender, "&c/event mod - Lets you moderate the Event system");
			
		}
		
		if (rank.hasRequiredRank(Rank.ADMIN)) {
			
			Chat.getInstance().messagePlayer(sender, "&c/event admin - Lets you change stuff related to the Event system");
			
		}
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m-----&r &5Event Commands &7&m-----"));
		
	}
	
}
