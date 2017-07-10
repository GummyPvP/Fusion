package fusion.utils.editing.cmds;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 27, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class RegionList {
	
	@Command(name = "regions", aliases = { "listregions", "regionlist" }, description = "Lists the regions loaded.", usage = "/regions", permission = "region.list")
	public void regionList(CommandArgs args) {
		
		Chat.getInstance().messagePlayer(args.getSender(), Chat.SECONDARY_BASE + "Regions: ");
		
		String temp = "";
		
		for (Region region : RegionManager.getInstance().getRegions()) {
			
			if (region instanceof ProtectedRegion) {
				
				ProtectedRegion protectedRegion = (ProtectedRegion) region;
				
				temp = protectedRegion.getName() + " - Flags - " + "PVP: " + (protectedRegion.isPVPEnabled() ? Chat.SECONDARY_BASE + "enabled," : Chat.IMPORTANT_COLOR + "disabled,")
						+ Chat.BASE_COLOR + " Refills: " + (protectedRegion.areRefillsAllowed() ? Chat.SECONDARY_BASE + "enabled," : Chat.IMPORTANT_COLOR + "disabled,")
						+ Chat.BASE_COLOR + " Healing Item: " + Chat.SECONDARY_BASE + protectedRegion.getHealingItem().toString();
				
				Chat.getInstance().messagePlayer(args.getSender(), Chat.BASE_COLOR + temp);
				
				temp = "";
				
				args.getSender().sendMessage("");
			}
			
		}
	}

}
