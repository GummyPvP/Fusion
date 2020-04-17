package fusion.utils.duels;

import java.util.HashSet;
import java.util.Set;

public class DuelManager {
	
	// assign duels, with appropriate team, map and kit
	
	private DuelManager() { }
	
	private static DuelManager instance = new DuelManager();
	
	public static DuelManager get() {
		return instance;
	}
	
	public void init() {
		this.teams = new HashSet<DuelTeam>();
		DuelArenas.get().loadArenas();
	}
	
	private Set<DuelTeam> teams;
	private Set<DuelTeam> inRankedQueue;
	
	public void addTeam(DuelTeam team) {
		this.teams.add(team);
	}
	
	public void removeTeam(DuelTeam team) {
		this.teams.remove(team);
	}
	
	public void addTeamToRankedQueue(DuelTeam team) {
		this.inRankedQueue.add(team);
	}
	
	public void removeTeamFromRankedueue(DuelTeam team) {
		this.inRankedQueue.remove(team);
	}
	
	public void startRankedSearch(DuelTeam team) {
		
		if (DuelArenas.get().findArenasWithCorrectSpawnCount(team.getPlayers().size() * 2).isEmpty()) { // x2 because there should be enough spots for a team of an equal size to join
			team.messagePlayers("&c&lSorry, there are currently no maps setup to handle a team of your size :(");
			return;
		}
		
		addTeamToRankedQueue(team);
		
		for (DuelTeam duelTeam : inRankedQueue) {
			
			if (duelTeam.getPlayers().size() != team.getPlayers().size()) continue;
			if (Math.abs(duelTeam.getAverageElo() - team.getAverageElo()) > 500) continue; // this value should be tweaked, we just don't want two teams with extremely varied skill to face each other
			
			// we should be good to start a duel with this team if all conditions are correct
			
			startRankedDuel(team, duelTeam);
			
		}
		
	}
	
	public void startRankedDuel(DuelTeam team1, DuelTeam team2) {
		DuelArena arena = DuelArenas.get().findArena(team1, team2);
		
		arena.teleportTeam(0, team1);
		arena.teleportTeam(1, team2);
		
		inRankedQueue.remove(team1);
		inRankedQueue.remove(team2);
	}
}
