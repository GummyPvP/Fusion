package fusion.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fusion.events.utils.Arena;
import fusion.events.utils.EventType;

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
	
	public void registerMap(EventType type, Arena arena) {
		
		List<Arena> tempList = arenas.get(type);
		
		if (tempList == null || tempList.isEmpty()) return;
		
		tempList.add(arena);
		
		arenas.put(type, tempList);
		
	}
	
	public void registerMaps() {
		
		
		
	}
	
	public List<Arena> getArenas(EventType type) {
		return arenas.get(type);
	}
	
	

}
