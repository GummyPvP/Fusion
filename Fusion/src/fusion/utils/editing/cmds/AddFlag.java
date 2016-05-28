package fusion.utils.editing.cmds;

import fusion.utils.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.ProtectedRegion.HealingItem;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class AddFlag {
	
	@Command(name = "addflag", description = "Adds flags to a region.", usage = "/addflag (region) (flag) (modifier)", rank = Rank.ADMIN)
	public void addFlag(CommandArgs args) {
		
		if (args.length() < 3) {
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "Usage: /addflag (region) (flag) (modifier)");
			
			return;
		}
		
		String regionName = args.getArgs(0);
		String flag = args.getArgs(1);
		String modifier = args.getArgs(2);
		
		if (!RegionManager.getInstance().regionExists(regionName)) {
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "That region doesn't exist!");
			
			return;
		}
		
		Region region = RegionManager.getInstance().getRegion(regionName);
		
		if (!(region instanceof ProtectedRegion)) {
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "That region is not correct!");
			
			return;
		}
		
		ProtectedRegion protectedRegion = (ProtectedRegion) region;
		
		switch (flag.toLowerCase()) {
		case "pvp":
		case "pvpenable":
		case "enablepvp":
			
			if (modifier.equalsIgnoreCase("yes") || modifier.equalsIgnoreCase("true") || modifier.equalsIgnoreCase("on")) {
				
				protectedRegion.togglePVP(true);
				Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Sucessfully enabled PVP!");
				break;
			}
			
			if (modifier.equalsIgnoreCase("no") || modifier.equalsIgnoreCase("false") || modifier.equalsIgnoreCase("off")) {
				
				protectedRegion.togglePVP(false);
				Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Sucessfully disabled PVP!");
				break;
			}

		default:
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "Flags: pvp, refills, item");
			
			break;
			
		case "refills":

			if (modifier.equalsIgnoreCase("yes") || modifier.equalsIgnoreCase("true") || modifier.equalsIgnoreCase("on")) {
				
				protectedRegion.toggleRefills(true);
				Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Sucessfully enabled refills!");
				break;
			}
			
			if (modifier.equalsIgnoreCase("no") || modifier.equalsIgnoreCase("false") || modifier.equalsIgnoreCase("off")) {
				
				protectedRegion.toggleRefills(false);
				Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Sucessfully disabled refills!");
				break;
			}
			
			break;
			
		case "health":
		case "healthitem":
			
			HealingItem item = null;
			
			try {
				
				item = HealingItem.valueOf(modifier.toUpperCase());
				
			} catch (IllegalArgumentException e) {
				
				Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "That's not an option! Use ANY, SOUP, POTION, GAPPLE");
				
				return;
				
			}
			
			protectedRegion.setHealthItem(item == null ? HealingItem.SOUP : item);
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Successfully changed healing item to " + (item == null ? HealingItem.SOUP : item).toString());
			
			break;
		}
		
	}

}
