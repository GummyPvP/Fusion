package fusion.utils.editing.cmds;

import org.bukkit.entity.Player;

import fusion.utils.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.editing.EditorSession;
import fusion.utils.editing.EditorSessions;
import fusion.utils.editing.editors.RegionEditor;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.RegionManager;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 27, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class RegionCreate {
	
	@Command(name = "createregion", aliases = { "cr", "region.create" }, description = "Creates a protected region.", usage = "/createregion (name)", rank = Rank.ADMIN, inGameOnly = true)
	public void regionCreate(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (EditorSessions.getInstance().getSession(player, new RegionEditor()) == null) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "You do not have two points selected!");
			
			return;
		}
		
		EditorSession session = EditorSessions.getInstance().getSession(player, new RegionEditor());
		
		if (!(session.getEditor() instanceof RegionEditor)) return;
		
		RegionEditor editor = (RegionEditor) session.getEditor();
		
		if ((editor.getPosition1() == null) || (editor.getPosition2() == null)) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "You do not have two points selected!");
			
			return;
		}
		
		if (args.length() < 1) {
			
			Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "Usage: /createregion (name)");
			
			return;
		}
		
		ProtectedRegion region = new ProtectedRegion(args.getArgs(0), player.getWorld(), editor.getPosition1(), editor.getPosition2());
		
		RegionManager.getInstance().registerRegion(region);
		
		Chat.getInstance().messagePlayer(player, Chat.STAFF_NOTIFICATION + "Region successfully created with name '" + region.getName() + "'!");
		
	}
	
}
