package fusion.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fusion.events.utils.Arena;
import fusion.events.utils.EventType;
import fusion.main.Fusion;
import fusion.utils.ConfigManager;

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
		arenas.get(type).add(arena);
	}
	
	public void registerArena() {
		
	}
	
	public void saveArenas() {
		

		
	}
	
}
