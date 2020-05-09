package fusion.utils.duels.cmds;

import org.bukkit.entity.Player;

import fusion.utils.Utils;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.duels.DuelArena;
import fusion.utils.duels.DuelArenas;

public class SetDuelSpawn {
	
	@Command(name = "setduelspawn", aliases = { }, description = "Creates a duel arena spawnpoint", usage = "/setduelspawn (name) (teamNumber)", inGameOnly = true)
	public void setDuelSpawn(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (args.length() < 2) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "Usage: /setduelspawn (name) (teamNumber)");
			
			return;
		}
		
		if (!Utils.isNumber(args.getArgs(1))) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "Please use a number for argument 2! Example: 0, 1");
			
			return;
		}
		
		DuelArena arena = DuelArenas.get().getArena(args.getArgs(0));
		
		if (arena == null) {
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "No arena exists with name '" + args.getArgs(0) + "'!");
			return;
		}
		
		arena.addSpawnPoint(Integer.valueOf(args.getArgs(1)), player.getLocation());
		
		Chat.getInstance().messagePlayer(player, Chat.STAFF_NOTIFICATION + "Spawnpoint added for team " + args.getArgs(1) + " in duel arena " + arena.getName() + "!");
		
	}

}
