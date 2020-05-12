package fusion.kits.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.editing.regions.RegionManager;

public class TarzanEvent implements Listener {
	
	private static int tarzanVineTime = 2;
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Tarzan")))
			return;
		
		if (player.getInventory().getItemInMainHand().getType() != Material.VINE) return;
		
		if (player.getVelocity().getY() > .55) {
			player.setVelocity(new Vector(player.getVelocity().getX(), .55, player.getVelocity().getZ()));
		}
		
		generateBlocks(player);
		
	}
	
	private void generateBlocks(Player player) {
		
		Location center = player.getLocation().add(new Vector(0, 1, 0));
		
		Vector min = center.toVector().subtract(new Vector(1, 0, 1));
		Vector max = center.toVector().add(new Vector(1, 2, 1));
		
		min = Vector.getMinimum(min, max);
		max = Vector.getMaximum(min, max);
		
		Set<Block> blocks = new HashSet<Block>();
		
		Bukkit.getScheduler().runTaskLater(Fusion.getInstance(), new Runnable() {
			
			public void run() {
				
				for (Block block : blocks) {
					block.setType(Material.AIR);
				}
				
			}
			
		}, tarzanVineTime * 20);
		
		Block currentBlock = center.add(center.getDirection()).getBlock();
		
		System.out.println(currentBlock.getType());
		
//		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
//			for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
//				for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
					//Block currentBlock = center.getWorld().getBlockAt(x, y, z);
					
					if (RegionManager.getInstance().isInProtectedRegion(currentBlock.getLocation())) return;
					
					if (currentBlock.getType() != Material.AIR) {
						
//						if (currentBlock.isPassable()) return;
//						
//						if (currentBlock.getType() == Material.VINE) return;
//						
//						if (currentBlock.isLiquid()) return;
//						
//						if (!currentBlock.getType().isSolid()) return;
//						
//						if (currentBlock.getFace(center.getBlock()) == null) return;
						
						Block frontBlock = currentBlock.getRelative(currentBlock.getFace(center.getBlock()));
						
						System.out.println("frontBlock: " + frontBlock.getType());
						
						//if (frontBlock == null) return;
						
						if (frontBlock.getType() != Material.AIR) return;
						
						frontBlock.setType(Material.VINE);
						
						blocks.add(frontBlock);
						
						player.setVelocity(player.getVelocity().add(new Vector(0, .55, 0)));
						
						if (frontBlock.getBlockData() instanceof MultipleFacing) {
							MultipleFacing data = (MultipleFacing) frontBlock.getBlockData();
							
						      for (BlockFace face : data.getAllowedFaces()) {
						    	  if (currentBlock.getFace(center.getBlock()).getOppositeFace() == face) {
						    		  data.setFace(currentBlock.getFace(center.getBlock()).getOppositeFace(), true);
						    		  frontBlock.setBlockData(data);
						    	  }
						      }
						}
//					}
//				}
//			}
		}
		
	}

}
