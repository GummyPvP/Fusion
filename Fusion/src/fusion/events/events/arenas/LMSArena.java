package fusion.events.events.arenas;

import java.util.Set;

import org.bukkit.Location;

import fusion.events.utils.Arena;
import fusion.utils.protection.Bounds;

/**
	 * 
	 * Created on Dec 23, 2016 by Jeremy Gooch.
	 * 
	 */

public class LMSArena extends Arena {

	private String name;
	private Bounds bounds;
	private Location mainSpawn;
	
	public LMSArena(String name, Bounds bounds, Location mainSpawn) {
		
		this.name = name;
		this.bounds = bounds;
		this.mainSpawn = mainSpawn;
		
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Bounds getBounds() {
		return bounds;
	}

	@Override
	public Location getMainSpawn() {
		return mainSpawn;
	}
	
	@Override
	public Set<Location> getAlternateSpawns() {
		return null;
		
	}

}
