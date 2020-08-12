package fusion.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class ListPlayers {
	
	@Command(name = "list", description = "List currently online players", usage = "/list",aliases = { "online", "who" })
	public void listPlayersCommand(CommandArgs args) {
		
		String playerList = "";
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			if (args.isPlayer() && (!args.getPlayer().canSee(player))) continue;
			
			playerList += (player.isOp() ? "&c" : "&7") + player.getName() + "&8, ";
			
		}
		
		playerList = playerList.replaceAll(", $", "");
		
		args.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Online players &8(&e" + Bukkit.getOnlinePlayers().size() + "&6/&e" + Bukkit.getMaxPlayers() + "&8)"));
		args.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', playerList.trim()));
		
	}

}
