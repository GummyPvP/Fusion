package fusion.utils.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat {

	private Chat() { }
	
	private static Chat instance = new Chat();
	
	public static Chat getInstance() {
		
		return instance;
		
	}
	
	public static final String CHAT_PREFIX = "&8» ";
	
	public static final ChatColor BASE_COLOR = ChatColor.GRAY, IMPORTANT_COLOR = ChatColor.RED, 
			DEBUG = ChatColor.DARK_RED, SECONDARY_BASE = ChatColor.GREEN, STAFF_NOTIFICATION = ChatColor.YELLOW;
	
	public static final String ALREADY_USED_KIT = BASE_COLOR + "You already have kit " + IMPORTANT_COLOR + "%s" + BASE_COLOR + "! Please type /clearkit to choose another kit.";
	
	public void messagePlayer(CommandSender sender, String message) {
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CHAT_PREFIX + BASE_COLOR + message));
		
	}
	
	public void broadcastMessage(String message) {
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			
			online.sendMessage(ChatColor.translateAlternateColorCodes('&', CHAT_PREFIX + BASE_COLOR + message));
			
		}
		
	}
}
