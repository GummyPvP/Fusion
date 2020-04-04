package fusion.utils.editing.cmds;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.editing.regions.Region;
import fusion.utils.editing.regions.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class RegionDelete {
	
	@Command(name = "deleteregion", description = "Deletes a region.", usage = "/deleteregion (name)", permission = "region.delete")
	public void regionDelete(CommandArgs args) {
		
		if (args.length() < 1) {
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "Usage: /deleteregion (name)");
			
			return;
		}
		
		String regionName = args.getArgs(0);
		
		if (!RegionManager.getInstance().regionExists(regionName)) {
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "Region " + regionName + " does not exist!");
			
			return;
		}
		
		Region region = RegionManager.getInstance().getRegion(regionName);
		
		RegionManager.getInstance().removeRegion(region);
		
		Chat.getInstance().messagePlayer(args.getSender(), Chat.SECONDARY_BASE + "Region " + regionName + " removed!");
		
	}
	
}
