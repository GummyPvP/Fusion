package fusion.cmds;

import org.bukkit.entity.Player;

import fusion.main.Fusion;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Created on Dec 4, 2016 by Jeremy Gooch.
	 * 
	 */

public class SetGladiator {
	
	@Command(name = "setgladiator", description = "Set gladiator spawn positions", usage = "/setgladiator (position)", permission = "gladiator.set")
	public void setGladiatorCommand(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (args.length() != 1) {
			
			Chat.getInstance().messagePlayer(player, "&c/setgladiator (position)");
			
			return;
		}
		
		if (args.getArgs(0).equalsIgnoreCase("attacker")) {
			
			Fusion.getInstance().getKitInfoFile().set("gladiator.world", player.getWorld().getName());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacker.x", player.getLocation().getX());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacker.y", player.getLocation().getY());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacker.z", player.getLocation().getZ());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacker.yaw", player.getLocation().getYaw());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacker.pitch", player.getLocation().getPitch());
			
			return;
		}
		
		if (args.getArgs(0).equalsIgnoreCase("attacked")) {
			
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacked.x", player.getLocation().getX());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacked.y", player.getLocation().getY());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacked.z", player.getLocation().getZ());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacked.yaw", player.getLocation().getYaw());
			Fusion.getInstance().getKitInfoFile().set("gladiator.attacked.pitch", player.getLocation().getPitch());
			
			return;
		}
		
		Chat.getInstance().messagePlayer(player, "&c/setgladiator attacked|attacker");
		
	}

}
