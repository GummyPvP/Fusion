package fusion.events.events.arenas;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;

import fusion.events.utils.Arena;
import fusion.main.Fusion;
import fusion.utils.ConfigManager;
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
	private Material item;
	
	public LMSArena(String name, Bounds bounds, Location mainSpawn, Material item) {
		
		this.name = name;
		this.bounds = bounds;
		this.mainSpawn = mainSpawn;
		this.item = item;
		
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
	
	@Override
	public Material getItemGUI() {
		return item;
	}
	
	@Override
	public void save() {
		
		ConfigManager file = Fusion.getInstance().getArenaFile();
		
		String arenaPath = "arenas.LMS." + getName();
		
		file.set(arenaPath + ".mainSpawn", getMainSpawn().serialize());
		
		file.set(arenaPath + ".bounds", bounds.serialize());
		
		file.set(arenaPath + ".item", getItemGUI().toString());
		
	}

}
