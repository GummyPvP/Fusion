package fusion.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;

import fusion.events.events.arenas.LMSArena;
import fusion.events.utils.Arena;
import fusion.events.utils.EventType;
import fusion.main.Fusion;
import fusion.utils.ConfigManager;
import fusion.utils.protection.Bounds;

/**
	 * 
	 * Created on Dec 23, 2016 by Jeremy Gooch.
	 * 
	 */

public class ArenaManager {
	
	private ArenaManager() { }
	
	private static ArenaManager instance = new ArenaManager();
	
	public static ArenaManager get() {
		return instance;
	}
	
	private Map<EventType, List<Arena>> arenas = new HashMap<EventType, List<Arena>>();
	
	public List<Arena> getArenas(EventType type) {
		return arenas.get(type);
	}
	
	public void registerArena(EventType type, Arena arena) {
		
		if (arenas.get(type) == null) {
			
			List<Arena> tempList = new ArrayList<Arena>();
			
			tempList.add(arena);
			
			arenas.put(type, tempList);
			
			return;
		}
		
		arenas.get(type).add(arena);
	}
	
	@SuppressWarnings("unchecked")
	public void loadArenas() {
		
		ConfigManager file = Fusion.getInstance().getArenaFile();
		
		if (file.getSection("arenas") == null) return;
		
		for (String object : file.getSection("arenas.LMS").getKeys(false)) {
			
			LMSArena arena = new LMSArena(object, Bounds.deserialize((Map<String, Object>) file.get("arenas.LMS." + object + ".mainSpawn")), 
					Location.deserialize((Map<String, Object>) file.get("arenas.LMS." + object + ".mainSpawn")), Material.valueOf(file.get("arenas.LMS." + object + ".item")));
			
			registerArena(EventType.LMS, arena);
			
		}
		
//		for (Object object :  Fusion.getInstance().getArenaFile().getSection("arenas.REDROVER").getKeys(false)) {
//			
//			
//			
//		}
		
	}
	
	public void saveArenas() {
		
		for (List<Arena> arenaList : arenas.values()) {
			
			for (Arena arena : arenaList) {
				
				arena.save();
				
			}
			
		}
		
	}
	
}
