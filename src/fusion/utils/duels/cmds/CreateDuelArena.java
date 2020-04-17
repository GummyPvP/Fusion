package fusion.utils.duels.cmds;

import org.bukkit.entity.Player;

import fusion.utils.Utils;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.duels.DuelArena;
import fusion.utils.duels.DuelArenas;
import fusion.utils.editing.EditorSession;
import fusion.utils.editing.EditorSessions;
import fusion.utils.editing.editors.RegionEditor;
import fusion.utils.editing.regions.ProtectedRegion;
import fusion.utils.editing.regions.RegionManager;

public class CreateDuelArena {
	
	@Command(name = "createduelarena", aliases = { "duelarena.create" }, description = "Creates a duel arena.", usage = "/createdualarena (name) (teamSize)", inGameOnly = true)
	public void createDuelArena(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (args.length() < 2) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "Usage: /createdualarena (name) (teamSize)");
			
			return;
		}
		
		if (DuelArenas.get().getArena(args.getArgs(0)) != null) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "That arena already exists!");
			
			return;
		}
		
		if (!Utils.isNumber(args.getArgs(1))) {
			
			return;
		}
		
		DuelArena arena = new DuelArena(player.getWorld(), args.getArgs(0), Integer.valueOf(args.getArgs(1)), null);
		
		// add
		
		Chat.getInstance().messagePlayer(player, Chat.STAFF_NOTIFICATION + "Region successfully created with name '" + arena.getName() + "'!");
		
	}

}
