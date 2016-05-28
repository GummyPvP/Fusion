package fusion.cmds;

import org.bukkit.Bukkit;

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
	 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Test {
	
	@Command(name = "test", rank=Rank.ADMINPLUS, description = "Test command", usage = "/test", inGameOnly = true)
	public void testCommand(CommandArgs args) {
		
		EditorSession session = EditorSessions.getInstance().getSession(args.getPlayer(), new RegionEditor());
		
		ProtectedRegion region = new ProtectedRegion("Test2", args.getPlayer().getWorld(), ((RegionEditor) session.getEditor()).getPosition1(), ((RegionEditor) session.getEditor()).getPosition2());
		
		RegionManager.getInstance().registerRegion(region);
		
		Bukkit.broadcastMessage(RegionManager.getInstance().getRegions().toString());
		
	}

}
