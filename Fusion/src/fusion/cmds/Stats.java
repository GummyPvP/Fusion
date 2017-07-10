package fusion.cmds;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fusion.main.Fusion;
import fusion.teams.utils.Team;
import fusion.teams.utils.TeamManager;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Stats {

	@SuppressWarnings("deprecation")
	@Command(name = "stats", aliases = { "gstats" }, description = "Shows your account statistics.", usage = "/stats")
	public void stats(CommandArgs args) {
		
		if (args.length() == 0) {
			
			if (!(args.getSender() instanceof Player)) {
				
				Chat.getInstance().messagePlayer(args.getSender(), "Usage: /stats <player>");
				
				return;
			}
			
			mKitUser user = mKitUser.getInstance(args.getPlayer());

			DecimalFormat dm = new DecimalFormat("#.##");

			int kills = user.getKills();
			int deaths = user.getDeaths();
			int killstreak = user.getKillStreak();
			double candies = user.getCandies();

			double kd = 0;

			if (kills != 0) {

				kd = (double) kills / (double) deaths;

			}
			
			String kdr = dm.format(kd);
			Team team = user.getTeam();

			Chat.getInstance().messagePlayer(args.getSender(), "&8&m----- &a" + args.getPlayer().getName() + " &a(Online) &8&m----");
			Chat.getInstance().messagePlayer(args.getSender(), "&aCandies: &f" + candies);
			Chat.getInstance().messagePlayer(args.getSender(), "&aKills: &f" + kills);
			Chat.getInstance().messagePlayer(args.getSender(), "&aDeaths: &f" + deaths);
			Chat.getInstance().messagePlayer(args.getSender(), "&aK/D Ratio: &f" + kdr);
			Chat.getInstance().messagePlayer(args.getSender(), "&aKillStreak: &f" + killstreak);
			Chat.getInstance().messagePlayer(args.getSender(), "&aTeam: &f" + (team == null ? "none" : team.getName()));
			
		}
		
		if (args.length() == 1) {

			Player player = Bukkit.getPlayer(args.getArgs(0));

			if (player == null) {

				OfflinePlayer op = Bukkit.getOfflinePlayer(args.getArgs(0));
				
				if (op.hasPlayedBefore()) {
					
					DecimalFormat dm = new DecimalFormat("#.##");
					
					int kills = Fusion.getInstance().getPlayerFile(op.getName()).getInt("kills");
					int deaths = Fusion.getInstance().getPlayerFile(op.getName()).getInt("deaths");
					int killstreak = Fusion.getInstance().getPlayerFile(op.getName()).getInt("killstreak");
					int candies = Fusion.getInstance().getPlayerFile(op.getName()).getInt("profile.candies");
					
					double kd = 0;

					if (kills != 0) {

						kd = (double) kills / (double) deaths;

					}
					
					String kdr = dm.format(kd);
					Team team = null;
					
					for (Team t : TeamManager.get().getTeams()) {

						if (t.getMembers().keySet().contains(op.getUniqueId())) {
							team = t;
						}
					}

					Chat.getInstance().messagePlayer(args.getSender(), "&8&m----- &a" + op.getName() + " &c(offline) &8&m----");
					Chat.getInstance().messagePlayer(args.getSender(), "&aCandies: &f" + candies);
					Chat.getInstance().messagePlayer(args.getSender(), "&aKills: &f" + kills);
					Chat.getInstance().messagePlayer(args.getSender(), "&aDeaths: &f" + deaths);
					Chat.getInstance().messagePlayer(args.getSender(), "&aK/D Ratio: &f" + kdr);
					Chat.getInstance().messagePlayer(args.getSender(), "&aKillStreak: &f" + killstreak);
					Chat.getInstance().messagePlayer(args.getSender(), "&aTeam: &f" + (team == null ? "none" : team.getName()));

					return;
				}

				Chat.getInstance().messagePlayer(args.getSender(), "&cThis user was not found in the database.");
				return;
			}
			
			mKitUser user = mKitUser.getInstance(player);

			DecimalFormat dm = new DecimalFormat("#.##");

			int kills = user.getKills();
			int deaths = user.getDeaths();
			int killstreak = user.getKillStreak();
			double candies = user.getCandies();
			double kd = 0;

			if (kills != 0) {

				kd = (double) kills / (double) deaths;

			}
			
			String kdr = dm.format(kd);
			Team team = user.getTeam();

			Chat.getInstance().messagePlayer(args.getSender(), "&8&m----- &a" + player.getName() + " &a(Online) &8&m----");
			Chat.getInstance().messagePlayer(args.getSender(), "&aCandies: &f" + candies);
			Chat.getInstance().messagePlayer(args.getSender(), "&aKills: &f" + kills);
			Chat.getInstance().messagePlayer(args.getSender(), "&aDeaths: &f" + deaths);
			Chat.getInstance().messagePlayer(args.getSender(), "&aK/D Ratio: &f" + kdr);
			Chat.getInstance().messagePlayer(args.getSender(), "&aKillStreak: &f" + killstreak);
			Chat.getInstance().messagePlayer(args.getSender(), "&aTeam: &f" + (team == null ? "none" : team.getName()));

			return;

		}
	}
}
