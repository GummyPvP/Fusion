package fusion.cmds;

import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;

import fusion.utils.CandyMan;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class CandyManCommands {
	
	@Command(name = "candyman", description = "See candyman information.", usage = "/candyman (args...)")
	public void candyMan(CommandArgs args) {                             
		
		
		
	}
	
	@Command(name = "candyman.spawn", description = "Spawn candyman.", usage = "/candyman spawn", rank = Rank.ADMIN, inGameOnly = true)
	public void candyManSpawn(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		CraftWorld handle = (CraftWorld) player.getWorld();
		
		CandyMan candyMan = new CandyMan(handle.getHandle());
		
		handle.getHandle().addEntity(candyMan);
		
		candyMan.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		
		
	}

}
