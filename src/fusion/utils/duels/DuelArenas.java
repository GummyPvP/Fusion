package fusion.utils.duels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DuelArenas {
	
	private DuelArenas() { 
		this.arenas = new HashSet<DuelArena>();
	}
	
	private static DuelArenas instance = new DuelArenas();
	
	public static DuelArenas get() {
		return instance;
	}
	
	private Set<DuelArena> arenas;
	
	public void loadArenas() {
		
	}
	
	public void saveArenas() {
		
	}
	
	public Set<DuelArena> getArenas() {
		return arenas;
	}
	
	public DuelArena getArena(String name) {
		
		for (DuelArena arena : getArenas()) {
			if (arena.getName().equalsIgnoreCase(name)) return arena;
		}
		
		return null;
		
	}
	
	public List<DuelArena> findArenasWithCorrectSpawnCount(int spawnCount) {
		
		List<DuelArena> result = new ArrayList<DuelArena>();
		
		for (DuelArena arena : arenas) {
			if (arena.getSpawnPointCount() != spawnCount) continue;
			result.add(arena);
		}
		
		return result;
	}
	
	public DuelArena findArena(DuelTeam team1, DuelTeam team2) {
		
		List<DuelArena> arenas = findArenasWithCorrectSpawnCount(team1.getPlayers().size() + team2.getPlayers().size());
		
		return arenas.get(new Random(arenas.size()).nextInt()); // pick a random map out of the potential map pool
		
	}

}
