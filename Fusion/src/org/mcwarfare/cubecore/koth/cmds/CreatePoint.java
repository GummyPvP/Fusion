package org.mcwarfare.cubecore.koth.cmds;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.gameplay.Point;
import org.mcwarfare.cubecore.koth.gameplay.Points;
import org.mcwarfare.cubecore.koth.gameplay.managers.PointRegionManager;
import org.mcwarfare.cubecore.koth.utils.Day;
import org.mcwarfare.cubecore.koth.utils.region.Bounds;
import org.mcwarfare.cubecore.koth.utils.regions.PointRegion;

import com.mysql.jdbc.Messages;
import com.sk89q.worldedit.bukkit.selections.Selection;

import fusion.utils.Utils;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Created on Jul 12, 2016 by Jeremy Gooch.
	 * 
	 */

public class CreatePoint {
	
	@Command(name = "createpoint", permission = "koth.point.create", inGameOnly = true)
	public void createPointCommand(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		if (Fusion.getInstance().getWorldEdit() == null) {
			
			Chat.getInstance().messagePlayer(player, "&cWorldEdit is not installed on this server.");
			
			return;
		}
		
		Selection selection = Fusion.getInstance().getWorldEdit().getSelection(player);
		
		if (selection == null) {
			
			Chat.getInstance().messagePlayer(player, "&cPlease select two points with the WorldEdit wand.");
			
			return;
		}
		
		if (selection.getMinimumPoint() == null || selection.getMaximumPoint() == null) {
			
			Chat.getInstance().messagePlayer(player, "&cPlease select two points with the WorldEdit wand.");
			
			return;
		}
		
		if (args.length() != 3) {
			
			Chat.getInstance().messagePlayer(player, "&cUsage: /createpoint <name> <day> <time>");
			
			return;
		}
		
		if (!Utils.isInt(args.getArgs(2).replaceFirst(":", ""))) {
			
			Chat.getInstance().messagePlayer(player, "&cUsage: /createpoint <name> <day> <time>");
			
			return;
		}
		
		String name = args.getArgs(0);
		String day = args.getArgs(1);
		
		String[] time = args.getArgs(2).split(":");
		
		int hour = Integer.parseInt(time[0]);
		int minutes = Integer.parseInt(time[1]);
		
		if (hour < 0 || hour > 24) {
			
			Chat.getInstance().messagePlayer(player, "&cPlease provide a correct hour, 0-24");
			
			return;
		}
		
		if (minutes < 0 || minutes > 59) {
			
			Chat.getInstance().messagePlayer(player, "&cPlease provide a correct number of minutes, 0-59");
			
			return;
		}
		
		Vector minPoint = selection.getMinimumPoint().toVector();
		Vector maxPoint = selection.getMaximumPoint().toVector();
		
		Day futureDay = Day.getByName(day);
		
		if (futureDay == null) {
			
			Chat.getInstance().messagePlayer(player, "&cYou did not supply a correct day.");
			
			return;
		}
		
		PointRegion region = new PointRegion(args.getArgs(0), new Bounds(player.getWorld(), minPoint, maxPoint));
		
		Point point = new Point(name, region, Fusion.getInstance().getDefaultCaptureTime(), futureDay, hour, minutes);
		
		// save and register
		
		PointRegionManager.get().addRegion(region);
		
		Points.get().registerPoint(point);
		
		region.save();
		
		point.save();
		
		Chat.getInstance().messagePlayer(args.getSender(), Messages.KOTH_CREATED);
		
	}
}
