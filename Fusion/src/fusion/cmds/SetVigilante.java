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

public class SetVigilante {
	
	@Command(name = "setvigilante", description = "Set vigilante spawn position", usage = "/setvigilante", permission = "vigilante.set")
	public void setGladiatorCommand(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		Fusion.getInstance().getKitInfoFile().set("vigilante.world", player.getWorld().getName());
		Fusion.getInstance().getKitInfoFile().set("vigilante.x", player.getLocation().getX());
		Fusion.getInstance().getKitInfoFile().set("vigilante.y", player.getLocation().getY());
		Fusion.getInstance().getKitInfoFile().set("vigilante.z", player.getLocation().getZ());
		Fusion.getInstance().getKitInfoFile().set("vigilante.yaw", player.getLocation().getYaw());
		Fusion.getInstance().getKitInfoFile().set("vigilante.pitch", player.getLocation().getPitch());
		
		Chat.getInstance().messagePlayer(player, "&cVigilante spawn position set!");
		
	}

}
