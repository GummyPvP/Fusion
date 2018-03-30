package fusion.kits.utils.kitutils;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import fusion.utils.mKitUser;

/**
	 * 
	 * Created on Mar 17, 2018 by Jeremy Gooch.
	 * 
	 */

public class GladiatorManager {
	
	private GladiatorManager() { }
	
	private static GladiatorManager instance = new GladiatorManager();
	
	public static GladiatorManager getInstance() {
		return instance;
	}
	
	private Set<GladiatorArena> arenas = new HashSet<GladiatorArena>();
	
	public Set<GladiatorArena> getArenas() {
		return arenas;
	}
	
	public GladiatorArena getArena(Player player) {
		
		for (GladiatorArena arena : arenas) {
			
			if (arena.getAttacked() == player || arena.getAttacker() == player) return arena;
			
		}
		
		return null;
		
	}
	
	public void startFight(Player attacker, Player attacked) {
		
		GladiatorArena arena = new GladiatorArena(mKitUser.getInstance(attacker), mKitUser.getInstance(attacked));
		
		arenas.add(arena);
		
		arena.beginDuel();
		
	}
	
	public void endFight(Player winner) {
		
		if (getArena(winner) == null) return;
		
		getArena(winner).endDuel(mKitUser.getInstance(winner));
		
		if (!arenas.remove(getArena(winner))) {
			
			// this is an issue
			
			getArena(winner).endDuel(mKitUser.getInstance(winner)); // I need to fix this properly somehow
			
		}
		
	}
}
