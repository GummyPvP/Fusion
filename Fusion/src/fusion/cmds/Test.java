package fusion.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fusion.events.EventManager;
import fusion.events.events.LMS;
import fusion.events.events.arenas.LMSArena;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.editing.EditorManager;
import fusion.utils.editing.EditorSession;
import fusion.utils.editing.EditorSessions;
import fusion.utils.editing.editors.RegionEditor;
import fusion.utils.protection.Bounds;
import mpermissions.utils.permissions.Rank;

/**
 * 
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Test {

	@Command(name = "test", rank = Rank.ADMINPLUS, description = "Test command", usage = "/test", inGameOnly = true)
	public void testCommand(CommandArgs args) {

		// TextUtils.MakeText("Jonhan is gay", args.getPlayer().getLocation(),
		// BlockFace.NORTH, Material.BEDROCK.getId(), (byte) 0,
		// TextAlign.CENTER);
		
		Bukkit.broadcastMessage("Test has executed");
		
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
		
		Bounds bounds = new Bounds(player.getWorld(), editor.getPosition1(), editor.getPosition2());
		
		LMSArena arena = new LMSArena("Test", bounds, bounds.getCenter().toLocation(bounds.getWorld()));
		
		LMS event = new LMS(arena, args.getSender().getName());
		
		EventManager.get().getActiveEvents().add(event);
		
		event.addPlayer(player);
		
	}
}
