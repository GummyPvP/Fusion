package org.mcwarfare.cubecore.koth.cmds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mcwarfare.cubecore.koth.gameplay.Point;
import org.mcwarfare.cubecore.koth.gameplay.Points;
import org.mcwarfare.cubecore.koth.gameplay.managers.RegionManager;
import org.mcwarfare.cubecore.koth.utils.region.Region;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Created on Jul 13, 2016 by Jeremy Gooch.
	 * 
	 */

public class Regions {
	
	@Command(name = "regions", permission = "koth.regions")
	public void regionsCommand(CommandArgs args) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for (Point point : Points.get().getPoints()) {
			
			stringBuilder.append(point.getName() + ", ");
			
		}
		
		String pointString = stringBuilder.toString();
		
		Pattern pointPattern = Pattern.compile(", $");
		Matcher pointMatcher = pointPattern.matcher(stringBuilder);
		
		pointString = pointMatcher.replaceAll("");
		
		Chat.getInstance().messagePlayer(args.getSender(), "&3Point Regions:");
		
		Chat.getInstance().messagePlayer(args.getSender(), "&e" + pointString);
		
		stringBuilder.setLength(0);
		
		for (Region region : RegionManager.get().getRegions()) {
			
			stringBuilder.append(region.getName() + ", ");
			
		}
		
		String kothRegions = stringBuilder.toString();
		
		Pattern kothPattern = Pattern.compile(", $");
		Matcher kothMatcher = kothPattern.matcher(stringBuilder);
		
		kothRegions = kothMatcher.replaceAll("");
		
		Chat.getInstance().messagePlayer(args.getSender(), "&3KOTH Regions (for messages)");
		
		Chat.getInstance().messagePlayer(args.getSender(), "&e" + kothRegions);
		
	}
	
}
