package fusion.utils.duels;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;

public class DuelTeam {
	
	private Set<String> players;
	private double averageElo;
	
	public DuelTeam(Set<String> players) {
		
		this.players = players;
		this.averageElo = calculateAverageElo();
		
	}
	
	private double calculateAverageElo() {
		
		double totalElo = 0.0;
		
		for (String playerName : this.players) {
			
			Player player = Bukkit.getPlayer(playerName);
			
			if (player == null) {
				players.remove(playerName);
				continue;
			}
			
			mKitUser user = mKitUser.getInstance(player);
			
			totalElo += user.getDuelElo();
			
		}
		
		return totalElo / players.size();
		
	}

	public Set<String> getPlayers() {
		return players;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player.getName());
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player.getName());
	}

	public void setPlayers(Set<String> players) {
		this.players = players;
	}

	public double getAverageElo() {
		return averageElo;
	}
	
	public void messagePlayers(String message) {
		
		for (String playerName : getPlayers()) {
			
			Player player = Bukkit.getPlayer(playerName);
			
			if (player == null) continue;
			
			Chat.getInstance().messagePlayer(player, message);
			
		}
		
	}

}
