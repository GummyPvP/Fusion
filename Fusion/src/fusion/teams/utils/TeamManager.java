package fusion.teams.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import fusion.main.Fusion;

/**
 * 
 * Created on Jul 16, 2016 by Jeremy Gooch.
 * 
 */

public class TeamManager {

	private TeamManager() {
	}

	private static TeamManager instance = new TeamManager();

	public static TeamManager get() {
		return instance;
	}

	private Set<Team> teams = new HashSet<Team>();

	public Set<Team> getTeams() {
		return teams;
	}

	public void createTeam(String name, Player start) {

		Map<UUID, TeamRank> members = new HashMap<UUID, TeamRank>();

		members.put(start.getUniqueId(), TeamRank.OWNER);

		Team team = new Team(name, members, new HashSet<UUID>());

		teams.add(team);

	}

	public void deleteTeam(String name) {

		if (getTeam(name) == null)
			return;

		teams.remove(TeamManager.get().getTeam(name));
		Fusion.getInstance().getTeamsFile().set("teams." + name, null);
	}

	public void loadTeam(String team) {

		if (!Fusion.getInstance().getTeamsFile().contains("teams." + team))
			return;

		Map<UUID, TeamRank> members = new HashMap<UUID, TeamRank>();

		for (String section : Fusion.getInstance().getTeamsFile().getSection("teams." + team + ".members")
				.getKeys(false)) {

			members.put(UUID.fromString(section), TeamRank
					.valueOf(Fusion.getInstance().getTeamsFile().getString("teams." + team + ".members." + section)));

		}

		// load invites

		HashSet<UUID> invitations = new HashSet<UUID>();

		if (Fusion.getInstance().getTeamsFile().getSection("teams." + team + ".invitations") != null) {

			for (String section : Fusion.getInstance().getTeamsFile().getSection("teams." + team + ".invitations")
					.getKeys(false)) {

				invitations.add(UUID.fromString(section));

				
			}

		}

		Team loadedTeam = new Team(team, members, invitations);

		teams.add(loadedTeam);

	}

	public void saveTeams() {

		for (Team t : getTeams()) {

			t.save();

		}

	}

	public boolean isTeam(String name) {

		for (Team t : getTeams()) {

			if (t.getName().equalsIgnoreCase(name))
				return true;

		}
		return false;
	}

	public void loadTeams() {

		if (Fusion.getInstance().getTeamsFile().contains("teams") == false)
			return;

		for (String section : Fusion.getInstance().getTeamsFile().getSection("teams").getKeys(false)) {

			loadTeam(section);

		}

	}

	public Team getTeam(UUID uuid) {

		for (Team team : teams) {

			if (team.getMembers().containsKey(uuid))
				return team;

		}
		return null;

	}

	public Team getTeam(String team) {

		for (Team teamz : teams) {

			if (teamz.getName().equalsIgnoreCase(team))
				return teamz;

		}

		return null;

	}

}
