package org.mcwarfare.cubecore.koth.cmds;

import org.bukkit.entity.Player;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.utils.region.Bounds;
import org.mcwarfare.cubecore.koth.utils.regions.KOTHRegion;

import com.mysql.jdbc.Messages;
import com.sk89q.worldedit.bukkit.selections.Selection;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
 * 
 * Created on Jul 11, 2016 by Jeremy Gooch.
 * 
 */

public class CreateRegion {

	@Command(name = "createregion", permission = "region.create", usage = "/createregion (name)")
	public void createRegionCommand(CommandArgs args) {

		Player player = args.getPlayer();
		
		if (Cubecore.getInstance().getWorldEdit() == null) {
			
			Chat.getInstance().messagePlayer(player, "&cWorldEdit is not installed on this server.");
			
			return;
		}
		
		Selection selection = Cubecore.getInstance().getWorldEdit().getSelection(args.getPlayer());
		
		if (selection == null) {
			
			Chat.getInstance().messagePlayer(player, "&cPlease select two points with the WorldEdit wand.");
			
			return;
		}
		
		if (selection.getMinimumPoint() == null || selection.getMaximumPoint() == null) {
			
			Chat.getInstance().messagePlayer(player, "&cPlease select two points with the WorldEdit wand.");
			
			return;
		}
		
		if (args.length() != 1) {
			
			Chat.getInstance().messagePlayer(player, "&cUsage: /createregion <name>");
			
			return;
		}
		
		KOTHRegion region = new KOTHRegion(args.getArgs(0), new Bounds(args.getPlayer().getWorld(), selection.getMinimumPoint().toVector(), selection.getMaximumPoint().toVector()));
		
		region.register();
		
		// make region manager and add it to it
		
		region.save();
		
		Chat.getInstance().messagePlayer(args.getSender(), Messages.REGION_CREATED);
	

	}

}
