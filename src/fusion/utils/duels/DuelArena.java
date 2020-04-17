package fusion.utils.duels;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class DuelArena {
	
	private World world;
	private String name;
	//private int teamCount;
	private int teamSize;
	private Map<Integer, List<Location>> spawnPoints;
	private int spawnPointCount;
	
	public DuelArena(World world, String name, int teamSize, Map<Integer, List<Location>> spawnPoints) {
		this.world = world;
		this.name = name;
		//this.teamCount = teamCount;
		this.teamSize = teamSize;
		this.spawnPoints = spawnPoints;
		this.spawnPointCount = calculateSpawnPointCount();
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public int getTeamCount() {
//		return teamCount;
//	}
//
//	public void setTeamCount(int teamCount) {
//		this.teamCount = teamCount;
//	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public Map<Integer, List<Location>> getSpawnPoints() {
		return spawnPoints;
	}

	public void setSpawnPoints(Map<Integer, List<Location>> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}
	
	public void addSpawnPoint(int teamNumber, Location location) {
		
		List<Location> locations = this.spawnPoints.get(teamNumber);
		locations.add(location);
		
		this.spawnPoints.put(teamNumber, locations);
	}
	
	public int getSpawnPointCount() {
		return spawnPointCount;
	}
	
	private int calculateSpawnPointCount() {
		int spawnPoints = 0;
		
		for (List<Location> locationList : getSpawnPoints().values()) {
			spawnPoints += locationList.size();
		}
		
		return spawnPoints;
	}
	
	public void teleportPlayer(int team, int spawnPoint, Player player) {
		
		List<Location> spawns = getSpawnPoints().get(team);
		
		Location spawn = spawns.get(spawnPoint);
		
		player.teleport(spawn);
		
		/* TODO: GIVE GEAR */
		
	}
	
	public void teleportTeam(int teamNumber, DuelTeam team) {
		
		int currentSpawn = 0;
		
		for (String playerName : team.getPlayers()) {
			
			Player player = Bukkit.getPlayer(playerName);
			
			if (player == null) continue;
			
			teleportPlayer(teamNumber, currentSpawn, player);
			
			currentSpawn++;
			
		}
		
	}

}
