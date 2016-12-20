package fusion.utils.protection;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.BlockState;
import org.bukkit.util.Vector;

/**
	 * 
	 * Created on Dec 6, 2016 by Jeremy Gooch.
	 * 
	 */

public class GladiatorArena {
	
	private Bounds bounds;
	
	private List<BlockState> oldBlockStates = new ArrayList<BlockState>();
	
	public GladiatorArena(Bounds bounds) {
		
		this.bounds = bounds;
		
	}
	
	public void setBlockStates() {
		
		Vector min = bounds.getMin();
		Vector max = bounds.getMax();
		
		int minX = min.getBlockX();
		int maxX = max.getBlockX();
		
		int minY = min.getBlockY();
		int maxY = max.getBlockY();
		
		int minZ = min.getBlockZ();
		int maxZ = max.getBlockZ();
		
		for (int x = minX; x < maxX; x++) {
			
			for (int y = minY; y < maxY; y++) {
				
				for (int z = minZ; z < maxZ; z++) {
					
					oldBlockStates.add(bounds.getWorld().getBlockAt(x, y, z).getState());
					
				}
				
			}
			
		}
		
	}

}
