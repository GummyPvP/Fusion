package fusion.cmds;

import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fusion.utils.CandyMan;
import fusion.utils.chat.Chat;
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
	
	@Command(name = "candyman", description = "See candyman information.", usage = "/candyman (args...)", rank = Rank.ADMIN)
	public void candyMan(CommandArgs args) {                             
		
		Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Spawn Candyman - /candyman spawn");
		Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Remove Candyman - /candyman remove");
		
	}
	
	@Command(name = "candyman.spawn", description = "Spawn candyman.", usage = "/candyman spawn", rank = Rank.ADMIN, inGameOnly = true)
	public void candyManSpawn(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		CraftWorld handle = (CraftWorld) player.getWorld();
		
		CandyMan candyMan = new CandyMan(handle.getHandle());
		
		candyMan.setPositionRotation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
		
		handle.getHandle().addEntity(candyMan);
		
		candyMan.setCustomName(Chat.SECONDARY_BASE + "Candy Man");
		
		Villager villager = (Villager) candyMan.getBukkitEntity();
		
		villager.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true));
		
	}

}
