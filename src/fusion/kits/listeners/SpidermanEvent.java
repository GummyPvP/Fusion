package fusion.kits.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.editing.regions.RegionManager;

public class SpidermanEvent implements Listener {

	private static int cobwebSpawnTime = 3;
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		
		if (event.getEntityType() != EntityType.SNOWBALL) return;
		
		if (!(event.getEntity().getShooter() instanceof Player)) return;
		
		Player shooter = (Player) event.getEntity().getShooter();
		
		if (!KitManager.getInstance().hasRequiredKit(shooter, KitManager.getInstance().valueOf("Spiderman")))
			return;
		
		if (event.getHitEntity() == null) {
			
			// do stuff for blocks directly
			
			generateBlocks(event.getHitBlock().getLocation().add(new Vector(0, 1, 0)));
			
			return;
		}
		
		if (!(event.getHitEntity() instanceof Player)) return;
		
		Player hit = (Player) event.getHitEntity();
		
		generateBlocks(hit.getLocation());
		
	}
	
	private void generateBlocks(Location center) {
		
		Vector min = center.toVector().subtract(new Vector(1, 0, 1));
		Vector max = center.toVector().add(new Vector(1, 0, 1));
		
		min = Vector.getMinimum(min, max);
		max = Vector.getMaximum(min, max);
		
		Set<Block> blocks = new HashSet<Block>();
		
		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
				Block currentBlock = center.getWorld().getBlockAt(x, center.getBlockY(), z);
				
				if (RegionManager.getInstance().isInProtectedRegion(currentBlock.getLocation())) continue;
				
				if (currentBlock.getType() != Material.AIR) continue;
				
				currentBlock.setType(Material.COBWEB);
				blocks.add(currentBlock);
			}
		}
		
		Bukkit.getScheduler().runTaskLater(Fusion.getInstance(), new Runnable() {
			
			public void run() {
				
				for (Block block : blocks) {
					block.setType(Material.AIR);
				}
				
			}
			
		}, cobwebSpawnTime * 20);
		
	}
	
}
