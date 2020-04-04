package fusion.teams.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fusion.main.Fusion;
import fusion.utils.chat.Chat;

/**
 * 
 * Created on Jul 16, 2016 by Jeremy Gooch.
 * 
 */

public class Team {

	private String name;
	private Map<UUID, TeamRank> players;
	private Set<UUID> invitations;
	ArrayList<UUID> chat = new ArrayList<UUID>();

	public Team(String name, Map<UUID, TeamRank> members, Set<UUID> invites) {

		this.name = name;
		this.players = members;
		this.invitations = invites;

	}

	public String getName() {
		return name;
	}

	public TeamRank getTeamRank(Player player) {

		for (UUID uuid : players.keySet()) {

			if (player.getUniqueId() == uuid)
				return players.get(uuid);

		}

		return null;

	}

	public ArrayList<UUID> getChat() {
		return chat;
	}

	public Map<UUID, TeamRank> getMembers() {
		return players;
	}

	public Set<UUID> getInvitedPlayers() {
		return invitations;
	}

	public boolean isOwner(UUID uuid) {

		TeamRank rank = getMembers().get(uuid);

		if (rank == TeamRank.OWNER) {

			return true;
		}

		return false;
	}

	public List<Player> getOnlineMemebers(Player getter) {

		ArrayList<Player> users = new ArrayList<Player>();

		for (Player online : Bukkit.getServer().getOnlinePlayers()) {

			for (UUID uuid : getMembers().keySet()) {

				if (online.getUniqueId().equals(uuid)) {

					if (!online.getUniqueId().equals(getter.getUniqueId()))

						users.add(online);

				}
			}
		}

		return users;
	}

	public void messageMembers(String message) {

		for (Player online : Bukkit.getServer().getOnlinePlayers()) {

			for (UUID uuid : getMembers().keySet()) {

				if (online.getUniqueId().equals(uuid)) {

					Chat.getInstance().messagePlayer(online, message);

				}

			}

		}

	}

	public void save() {

		Fusion.getInstance().getTeamsFile().set("teams." + name + ".invitations", null);

		for (UUID entry : invitations) {

			Fusion.getInstance().getTeamsFile().set("teams." + name + ".invitations." + entry.toString(), true);

		}

		Fusion.getInstance().getTeamsFile().set("teams." + name + ".members", null);

		for (Entry<UUID, TeamRank> entry : players.entrySet()) {

			Fusion.getInstance().getTeamsFile().set("teams." + name + ".members." + entry.getKey().toString(),
					entry.getValue().toString());

		}
		Fusion.getInstance().getTeamsFile().save();

	}

}
