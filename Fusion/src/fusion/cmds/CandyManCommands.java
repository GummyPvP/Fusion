package fusion.cmds;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class CandyManCommands {
	
//	@Command(name = "candyman", description = "See candyman information.", usage = "/candyman (args...)", rank = Rank.ADMIN)
//	public void candyMan(CommandArgs args) {                             
//		
//		Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Spawn Candyman - /candyman spawn");
//		Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Remove Candyman - /candyman remove");
//		
//	}
//	
//	@Command(name = "candyman.spawn", description = "Spawn candyman.", usage = "/candyman spawn", rank = Rank.ADMIN, inGameOnly = true)
//	public void candyManSpawn(CommandArgs args) {
//		
//		Player player = args.getPlayer();
//		
//		CraftWorld handle = (CraftWorld) player.getWorld();
//		
//		CandyMan candyMan = new CandyMan(handle.getHandle());
//		
//		candyMan.setPositionRotation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
//		
//		handle.getHandle().addEntity(candyMan, SpawnReason.SPAWNER_EGG);
//		
//	}
//
//	@Command(name = "candyman.remove", description = "Remove all candymen.", usage = "/candyman remove", rank = Rank.ADMIN, inGameOnly = true)
//	public void candyManRemove(CommandArgs args) {
//		
//		for (Entity ents : args.getPlayer().getLocation().getChunk().getEntities()) {
//			
//			if (ents instanceof Villager && ((Villager) ents).getCustomName() != null && ((Villager) ents).getCustomName().contains("Candy Man")) {
//				
//				ents.remove();
//				
//			}
//			
//		}
//		
//	}
	
}
