package fusion.teams.cmds;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fusion.listeners.CombatLog;
import fusion.teams.utils.Team;
import fusion.teams.utils.TeamManager;
import fusion.teams.utils.TeamRank;
import fusion.utils.StatsManager;
import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class TeamCommand {

	@SuppressWarnings({ "deprecation" })
	@Command(name = "team", permission = "cubecore.team", aliases = { "f", "t",
			"teams" }, description = "Team commands", inGameOnly = true, usage = "/team <args>")
	public void kit(CommandArgs args) {

		Player player = args.getPlayer();
		mKitUser user = mKitUser.getInstance(player);

		if (CombatLog.getInstance().isInCombat(player)) {

			Chat.getInstance().messagePlayer(player, "&cYou can not execute team commands while you are tagged!");

			return;
		}

		if (args.length() == 0) {

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " create <name> &e- Create your own new team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " teams &e- View all the teams on the server");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " disband &e- Disband your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " chat &e- Toggle to type to your team members");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " leave &e- Leave your currently team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " kick <player> &e-Kick a player from your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " join <name> &e-Join another user's team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " invite <player> &e- Invite a user to your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " uninvite <player> &e-Un-Invite a user to your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " setowner <name> &e- Give leadership of your team to another user");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " info &e- View information about your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " info <name> &e- View information on another team");

			return;
		}
		if (args.length() == 1) {

			if (args.getArgs(0).equalsIgnoreCase("chat") || args.getArgs(0).equalsIgnoreCase("teamchat")
					|| args.getArgs(0).equalsIgnoreCase("c")) {

				if (user.getTeam() == null) {

					Chat.getInstance().messagePlayer(player, "&cYou are not in a team!");

					return;
				}

				if (user.getTeam().getChat().contains(player.getUniqueId())
						? user.getTeam().getChat().remove(player.getUniqueId())
						: user.getTeam().getChat().add(player.getUniqueId()))
					;

				Chat.getInstance().messagePlayer(player,
						(user.getTeam().getChat().contains(player.getUniqueId())
								? ChatColor.GREEN + "TeamChat has been &benabled"
								: ChatColor.GREEN + "TeamChat has been &cdisabled"));
				return;
			}

			if (args.getArgs(0).equalsIgnoreCase("teams")) {

				StringBuilder sb = new StringBuilder();

				for (Team t : TeamManager.get().getTeams()) {

					sb.append(ChatColor.GREEN + t.getName() + ChatColor.WHITE + ", ");

				}

				Chat.getInstance().messagePlayer(player, Utils.removeLast(sb));

				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("leave")) {

				if (user.getTeam() == null) {

					Chat.getInstance().messagePlayer(player, "&cYou are currently not in a team!");

					return;
				}

				if (user.getTeam().isOwner(player.getUniqueId())) {

					Chat.getInstance().messagePlayer(player,
							"&aYou must give leadership of the team to another player to leave!");

					return;
				}

				user.getTeam().messageMembers("&e" + player.getName() + " &ahas left the team.");

				user.getTeam().getMembers().remove(player.getUniqueId());
				Chat.getInstance().messagePlayer(player, "&aYou have left the team.");
				StatsManager.getInstance().refreshScoreBoard(player, false);

				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("disband")) {

				if (user.getTeam() == null) {

					Chat.getInstance().messagePlayer(player, "&cYou currently do not have a team!");

					return;
				}

				if (user.getTeam().isOwner(player.getUniqueId())) {

					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							Chat.CHAT_PREFIX + "&b" + user.getTeam().getName() + " &ahas been disbanded!"));

					Chat.getInstance().messagePlayer(player, "&aYour team has been disbanded!");

					ArrayList<UUID> u = new ArrayList<UUID>();

					for (UUID uuid : user.getTeam().getMembers().keySet()) {

						u.add(uuid);

					}
					TeamManager.get().deleteTeam(user.getTeam().getName());

					for (Player p : Bukkit.getOnlinePlayers()) {

						for (UUID uuid : u) {

							if (p.getUniqueId().equals(uuid)) {

								if (p.getUniqueId().equals(player.getType()) == false) {

									StatsManager.getInstance().refreshScoreBoard((Player) Bukkit.getPlayer(uuid),
											CombatLog.getInstance().isInCombat((Player) Bukkit.getPlayer(uuid)));

								}
							}

						}

					}
					StatsManager.getInstance().refreshScoreBoard(player, false);

					return;

				} else {

					Chat.getInstance().messagePlayer(player, "&cYou must be the owner to disband the team!");

				}
				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("info")) {

				if (user.getTeam() == null) {

					Chat.getInstance().messagePlayer(player, "&cYou currently do have a team!");

					return;
				}

				Chat.getInstance().messagePlayer(player, "&b&m--------------------");

				Chat.getInstance().messagePlayer(player, "&aName: &e" + user.getTeam().getName());

				String owner = null;

				for (UUID uuid : user.getTeam().getMembers().keySet()) {

					if (user.getTeam().isOwner(uuid)) {

						Player p = Bukkit.getPlayer(uuid);

						if (p == null) {

							OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);

							if (op.getName() != null) {

								owner = op.getName();

							}

						} else {

							owner = p.getName();

						}
					}

				}

				Chat.getInstance().messagePlayer(player, "&aOwner: &e" + owner);

				StringBuilder members = new StringBuilder();

				for (UUID uuid : user.getTeam().getMembers().keySet()) {

					Player p = Bukkit.getPlayer(uuid);

					if (p == null) {

						OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);

						if (op.getName() != null) {

							members.append(ChatColor.YELLOW + op.getName() + ChatColor.WHITE + ", ");

						}

					} else {
						members.append(ChatColor.YELLOW + p.getName() + ChatColor.WHITE + ", ");

					}
				}

				StringBuilder online = new StringBuilder();

				for (UUID uuid : user.getTeam().getMembers().keySet()) {

					for (Player onlinee : Bukkit.getServer().getOnlinePlayers()) {

						if (onlinee.getUniqueId().equals(uuid)) {

							online.append(ChatColor.YELLOW + onlinee.getName() + ChatColor.WHITE + ", ");

						}

					}

				}

				Chat.getInstance().messagePlayer(player,
						"&aMembers: " + (members.toString() != null ? Utils.removeLast(members) : "&anone"));
				Chat.getInstance().messagePlayer(player,
						"&aOnline Players: " + (online.toString() != null ? Utils.removeLast(online) : "&anone"));
				Chat.getInstance().messagePlayer(player, "&b&m--------------------");

				return;
			}

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " create <name> &e- Create your own new team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " teams &e- View all the teams on the server");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " disband &e- Disband your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " chat &e- Toggle to type to your team members");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " leave &e- Leave your currently team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " kick <player> &e-Kick a player from your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " join <name> &e-Join another user's team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " invite <player> &e- Invite a user to your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " uninvite <player> &e-Un-Invite a user to your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " setowner <name> &e- Give leadership of your team to another user");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " info &e- View information about your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " info <name> &e- View information on another team");

			return;
		}
		if (args.length() == 2) {

			if (args.getArgs(0).equalsIgnoreCase("create")) {

				mKitUser kituser = mKitUser.getInstance(player);

				double after = kituser.getCandies() - 499;

				if (after <= 0) {

					double need = 499 - kituser.getCandies();

					Chat.getInstance().messagePlayer(player, "&cYou need &b" + need + " &cmore candies!");

					return;
				}

				if (args.getArgs(1).length() >= 16) {

					Chat.getInstance().messagePlayer(player, "&cYour team name cannot be longer than 16 characters!");

					return;
				}
				if (args.getArgs(1).contains("&")) {

					Chat.getInstance().messagePlayer(player, "&cYour team name can not contain color codes!");

					return;
				}
				if (!Utils.isAlpha(args.getArgs(1))) {

					Chat.getInstance().messagePlayer(player, "&cYour team name can only contains letters!");

					return;
				}

				if (user.getTeam() != null) {

					Chat.getInstance().messagePlayer(player, "&cYou already are in team " + user.getTeam().getName());

					return;
				}
				if (TeamManager.get().isTeam(args.getArgs(1))) {

					Chat.getInstance().messagePlayer(player, "&aThat team already exists!");

					return;
				}

				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
						Chat.CHAT_PREFIX + "&b" + args.getArgs(1) + " &ateam has been created!"));

				TeamManager.get().createTeam(args.getArgs(1), player);

				Chat.getInstance().messagePlayer(player, "&e" + args.getArgs(1)
						+ " &aTeam has been created and 500 candies were taken from your account!");

				StatsManager.getInstance().refreshScoreBoard(player, false);

				kituser.removeCandies(500);

				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("invite")) {

				if (args.getArgs(1).equals(player.getName())) {

					Chat.getInstance().messagePlayer(player, "&cYou can't invite yourself!");

					return;
				}

				if (user.getTeam() == null) {

					Chat.getInstance().messagePlayer(player, "&cYou are not currently in a team!");

					return;
				}
				if (!user.getTeam().isOwner(player.getUniqueId())) {

					Chat.getInstance().messagePlayer(player,
							"&cYou must be the owner to invite new members to the team!");

					return;
				}
				if (user.getTeam().getMembers().size() >= 5) {

					Chat.getInstance().messagePlayer(player, "&cYou can only have 5 members in your team!");

					return;
				}
				Player p = Bukkit.getPlayer(args.getArgs(1));

				if (p == null) {

					OfflinePlayer op = Bukkit.getOfflinePlayer(args.getArgs(1));

					if (op.getUniqueId() == null) {

						Chat.getInstance().messagePlayer(player, "&cThat player could not be found!");

						return;
					}

					if (user.getTeam().getMembers().containsKey(op.getUniqueId())) {

						Chat.getInstance().messagePlayer(player, "&cThat player is already in your team!");

						return;
					}
					if (user.getTeam().getInvitedPlayers().contains(op.getUniqueId())) {

						Chat.getInstance().messagePlayer(player,
								"&cThis player already has a invitation to your team!");

						return;
					}

					user.getTeam().getInvitedPlayers().add(op.getUniqueId());
					Chat.getInstance().messagePlayer(player, "&e" + op.getName() + " &ahas been invited to your team!");

					user.getTeam().messageMembers(
							"&e" + player.getName() + " &ahas invited &e" + op.getName() + " &ato the team!");

					return;
				}
				if (user.getTeam().getInvitedPlayers().contains(p.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cThis player already has a invitation to your team!");

					return;
				}

				if (user.getTeam().getMembers().containsKey(p.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cThat player is already in your team!");

					return;
				}
				user.getTeam().getInvitedPlayers().add(p.getUniqueId());
				Chat.getInstance().messagePlayer(p,
						"&e" + player.getName() + " &ahas invited you to team &b" + user.getTeam().getName());
				Chat.getInstance().messagePlayer(player, "&e" + p.getName() + " &ahas been invited to your team!");

				user.getTeam().messageMembers(
						"&e" + player.getName() + " &ahas invited &e" + p.getName() + " &ato the team!");

				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("uninvite")) {

				if (args.getArgs(1).equals(player.getName())) {

					Chat.getInstance().messagePlayer(player, "&cYou can't un-invite yourself!");

					return;
				}

				if (user.getTeam() == null) {

					Chat.getInstance().messagePlayer(player, "&cYou are currently not in a team!");

					return;
				}
				if (!user.getTeam().isOwner(player.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cYou must be the owner to revoke invitations!");

					return;
				}

				Player p = Bukkit.getPlayer(args.getArgs(1));

				if (p == null) {

					OfflinePlayer op = Bukkit.getOfflinePlayer(args.getArgs(1));

					if (op.getUniqueId() == null) {

						Chat.getInstance().messagePlayer(player, "&cThat player was not found");

						return;
					}

					if (!user.getTeam().getInvitedPlayers().contains(op.getUniqueId())) {

						Chat.getInstance().messagePlayer(player, "&cThat player does not have an invitation!");

						return;

					}

					user.getTeam().getInvitedPlayers().remove(op.getUniqueId());
					Chat.getInstance().messagePlayer(player, "&aThat user's invitation has been revoked!");

					user.getTeam().messageMembers("&e" + player.getName() + " &ahas revoked &e" + op.getName()
							+ " &ainvitation to the team!");

					return;
				}

				if (!user.getTeam().getInvitedPlayers().contains(p.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cThat player does not have an invitation!");

					return;

				}

				user.getTeam().getInvitedPlayers().remove(p.getUniqueId());
				Chat.getInstance().messagePlayer(player, "&aThat user's invitation has been revoked!");
				user.getTeam().messageMembers(
						"&e" + player.getName() + " &ahas revoked &e" + p.getName() + " &ainvitation to the team!");

				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("info")) {

				fusion.teams.utils.Team team = TeamManager.get().getTeam(args.getArgs(1));

				if (team == null) {

					Player pl = Bukkit.getPlayer(args.getArgs(1));

					if (pl == null) {

						OfflinePlayer op = Bukkit.getOfflinePlayer(args.getArgs(1));

						if (op == null) {

							Chat.getInstance().messagePlayer(player, "&cThat player was not found");

							return;
						}
						Team pt = TeamManager.get().getTeam(op.getUniqueId());

						if (pt == null) {

							Chat.getInstance().messagePlayer(player, "&cThat player is not in a team!");

							return;
						}

						Chat.getInstance().messagePlayer(player, "&b&m--------------------");

						Chat.getInstance().messagePlayer(player, "&aName: &e" + pt.getName());

						String owner = null;

						for (UUID uuid : pt.getMembers().keySet()) {

							if (pt.isOwner(uuid)) {

								Player p = Bukkit.getPlayer(uuid);

								if (p == null) {

									OfflinePlayer pop = Bukkit.getOfflinePlayer(uuid);

									if (pop.getName() != null) {

										owner = pop.getName();

									}

								} else {

									owner = p.getName();

								}
							}

						}

						Chat.getInstance().messagePlayer(player, "&aOwner: &e" + owner);

						StringBuilder members = new StringBuilder();

						for (UUID uuid : pt.getMembers().keySet()) {

							Player p = Bukkit.getPlayer(uuid);

							if (p == null) {

								OfflinePlayer pop = Bukkit.getOfflinePlayer(uuid);

								if (pop.getName() != null) {

									members.append(ChatColor.YELLOW + op.getName() + ChatColor.WHITE + ", ");

								}

							} else {
								members.append(ChatColor.YELLOW + p.getName() + ChatColor.WHITE + ", ");

							}
						}

						StringBuilder online = new StringBuilder();

						for (UUID uuid : pt.getMembers().keySet()) {

							for (Player onlinee : Bukkit.getServer().getOnlinePlayers()) {

								if (onlinee.getUniqueId().equals(uuid)) {

									online.append(ChatColor.YELLOW + onlinee.getName() + ChatColor.WHITE + ", ");

								}

							}

						}

						Chat.getInstance().messagePlayer(player,
								"&aMembers: " + (!members.toString().isEmpty() ? Utils.removeLast(members) : "&anone"));
						Chat.getInstance().messagePlayer(player, "&aOnline Players: "
								+ (!online.toString().isEmpty() ? Utils.removeLast(online) : "&anone"));
						Chat.getInstance().messagePlayer(player, "&b&m--------------------");

						return;

					}

					Team opt = TeamManager.get().getTeam(pl.getUniqueId());

					if (opt == null) {

						Chat.getInstance().messagePlayer(player, "&cThat player does not have a team!");

						return;
					}

					Chat.getInstance().messagePlayer(player, "&b&m--------------------");

					Chat.getInstance().messagePlayer(player, "&aName: &e" + opt.getName());

					String owner = null;

					for (UUID uuid : opt.getMembers().keySet()) {

						if (opt.isOwner(uuid)) {

							Player p = Bukkit.getPlayer(uuid);

							if (p == null) {

								OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);

								if (op.getName() != null) {

									owner = op.getName();

								}

							} else {

								owner = p.getName();

							}
						}

					}

					Chat.getInstance().messagePlayer(player, "&aOwner: &e" + owner);

					StringBuilder members = new StringBuilder();

					for (UUID uuid : opt.getMembers().keySet()) {

						Player p = Bukkit.getPlayer(uuid);

						if (p == null) {

							OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);

							if (op.getName() != null) {

								members.append(ChatColor.YELLOW + op.getName() + ChatColor.WHITE + ", ");

							}

						} else {
							members.append(ChatColor.YELLOW + p.getName() + ChatColor.WHITE + ", ");

						}
					}

					StringBuilder online = new StringBuilder();

					for (UUID uuid : opt.getMembers().keySet()) {

						for (Player onlinee : Bukkit.getServer().getOnlinePlayers()) {

							if (onlinee.getUniqueId().equals(uuid)) {

								online.append(ChatColor.YELLOW + onlinee.getName() + ChatColor.WHITE + ", ");

							}

						}

					}

					Chat.getInstance().messagePlayer(player,
							"&aMembers: " + (!members.toString().isEmpty() ? Utils.removeLast(members) : "&anone"));
					Chat.getInstance().messagePlayer(player, "&aOnline Players: "
							+ (!online.toString().isEmpty() ? Utils.removeLast(online) : "&anone"));
					Chat.getInstance().messagePlayer(player, "&b&m--------------------");

					return;
				}

				Chat.getInstance().messagePlayer(player, "&b&m--------------------");

				Chat.getInstance().messagePlayer(player, "&aName: &e" + team.getName());

				String owner = null;

				for (UUID uuid : team.getMembers().keySet()) {

					if (team.isOwner(uuid)) {

						Player p = Bukkit.getPlayer(uuid);

						if (p == null) {

							OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);

							if (op.getName() != null) {

								owner = op.getName();

							}

						} else {

							owner = p.getName();

						}
					}

				}

				Chat.getInstance().messagePlayer(player, "&aOwner: &e" + owner);

				StringBuilder members = new StringBuilder();

				for (UUID uuid : team.getMembers().keySet()) {

					Player p = Bukkit.getPlayer(uuid);

					if (p == null) {

						OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);

						if (op.getName() != null) {

							members.append(ChatColor.YELLOW + op.getName() + ChatColor.WHITE + ", ");

						}

					} else {
						members.append(ChatColor.YELLOW + p.getName() + ChatColor.WHITE + ", ");

					}
				}

				StringBuilder online = new StringBuilder();

				for (UUID uuid : team.getMembers().keySet()) {

					for (Player onlinee : Bukkit.getServer().getOnlinePlayers()) {

						if (onlinee.getUniqueId().equals(uuid)) {

							online.append(ChatColor.YELLOW + onlinee.getName() + ChatColor.WHITE + ", ");

						}

					}

				}

				Chat.getInstance().messagePlayer(player,
						"&aMembers: " + (!members.toString().isEmpty() ? Utils.removeLast(members) : "&anone"));
				Chat.getInstance().messagePlayer(player,
						"&aOnline Players: " + (!online.toString().isEmpty() ? Utils.removeLast(online) : "&anone"));
				Chat.getInstance().messagePlayer(player, "&b&m--------------------");

				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("kick")) {

				if (args.getArgs(1).equals(player.getName())) {

					Chat.getInstance().messagePlayer(player, "&cYou can't kick yourself!");

					return;
				}

				if (user.getTeam() == null) {

					Chat.getInstance().messagePlayer(player, "&cYou are currently not in a team!");

					return;
				}
				if (!user.getTeam().isOwner(player.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cYou must be the owner of the team to kick members!");

					return;
				}

				Player p = Bukkit.getPlayer(args.getArgs(1));

				if (p == null) {

					OfflinePlayer op = Bukkit.getOfflinePlayer(args.getArgs(1));

					if (op.getUniqueId() == null) {

						Chat.getInstance().messagePlayer(player, "&cThat player was not found!");

						return;
					}
					if (!user.getTeam().getMembers().containsKey(op.getUniqueId())) {

						Chat.getInstance().messagePlayer(player, "&cThat player is not in your team!");

						return;
					}
					user.getTeam().getMembers().remove(op.getUniqueId());
					Chat.getInstance().messagePlayer(player, "&aThat user has been kicked from the team!");

					user.getTeam().messageMembers(
							"&e" + player.getName() + " &ahas kick &e" + op.getName() + " &afrom the team!");

					return;
				}
				if (!user.getTeam().getMembers().containsKey(p.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cThat user is not in your team!");

					return;
				}

				user.getTeam()
						.messageMembers("&e" + player.getName() + " &ahas kick &e" + p.getName() + " &afrom the team!");
				user.getTeam().getMembers().remove(p.getUniqueId());
				StatsManager.getInstance().refreshScoreBoard(p, false);
				Chat.getInstance().messagePlayer(p, "&cYou have been kicked from your current team");
				Chat.getInstance().messagePlayer(player, "&aYou have kicked that player from your team");

				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("setowner")) {

				if (args.getArgs(1).equals(player.getName())) {

					Chat.getInstance().messagePlayer(player, "&cYou can't set yourself to your own owner's faction!");

					return;
				}

				if (user.getTeam() == null) {

					Chat.getInstance().messagePlayer(player, "&cYou are currently not in a team!");

					return;
				}
				if (!user.getTeam().isOwner(player.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cYou must the the owner of the team to set the owner!");

					return;
				}

				Player p = Bukkit.getPlayer(args.getArgs(1));

				if (p == null) {

					OfflinePlayer op = Bukkit.getOfflinePlayer(args.getArgs(1));

					if (op.getUniqueId() == null) {

						Chat.getInstance().messagePlayer(player, "&cThat player was not found!");

						return;
					}
					if (!user.getTeam().getMembers().containsKey(op.getUniqueId())) {

						Chat.getInstance().messagePlayer(player, "&cThat player is not in your team!");

						return;
					}

					Team t = user.getTeam();

					t.getMembers().remove(op.getUniqueId());
					t.getMembers().remove(player.getUniqueId());

					t.getMembers().put(player.getUniqueId(), TeamRank.MEMBER);
					t.getMembers().put(op.getUniqueId(), TeamRank.OWNER);

					Chat.getInstance().messagePlayer(player, "&aTeam ownership was transfered to &e" + op.getName());

					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							Chat.CHAT_PREFIX + "&aLeadership of &b" + t.getName() + " &ahas been given to &e"
									+ op.getName() + " &afrom &e" + player.getName()));

					return;
				}
				if (!user.getTeam().getMembers().containsKey(p.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cThat player is not in your team!");

					return;

				}

				Team t = user.getTeam();

				t.getMembers().remove(p.getUniqueId());
				t.getMembers().remove(player.getUniqueId());

				t.getMembers().put(player.getUniqueId(), TeamRank.MEMBER);
				t.getMembers().put(p.getUniqueId(), TeamRank.OWNER);

				Chat.getInstance().messagePlayer(player, "&aTeam ownership was transfered to &e" + p.getName());

				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
						Chat.CHAT_PREFIX + "&aLeadership of &b" + t.getName() + " &ahas been given to &e" + p.getName()
								+ " &afrom &e" + player.getName()));

				return;
			}
			if (args.getArgs(0).equalsIgnoreCase("join")) {

				Team team = TeamManager.get().getTeam(args.getArgs(1));

				if (team == null) {

					Player p = Bukkit.getPlayer(args.getArgs(1));

					if (p == null) {

						OfflinePlayer op = Bukkit.getOfflinePlayer(args.getArgs(1));

						if (op.getUniqueId() == null) {

							Chat.getInstance().messagePlayer(player, "&cThat player was not found");

							return;
						}
						Team t = TeamManager.get().getTeam(op.getUniqueId());

						if (t == null) {

							Chat.getInstance().messagePlayer(player, "&cThat player does not have a team");

							return;
						}
						if (!t.getInvitedPlayers().contains(player.getUniqueId())) {

							Chat.getInstance().messagePlayer(player,
									"&cYou currently have no invitation to that team!");

							return;
						}
						if (t.getMembers().size() >= 5) {

							Chat.getInstance().messagePlayer(player, "&cThat team already has 5 members!!");

							return;
						}

						t.getInvitedPlayers().remove(player.getUniqueId());
						t.getMembers().put(player.getUniqueId(), TeamRank.MEMBER);

						user.getTeam().messageMembers("&e" + player.getName() + " &ahas joined the team!");

						StatsManager.getInstance().refreshScoreBoard(player, false);

						Chat.getInstance().messagePlayer(player, "&aYou have joined team &b" + t.getName());

						return;
					}
					Team ot = TeamManager.get().getTeam(p.getUniqueId());

					if (ot == null) {

						Chat.getInstance().messagePlayer(player, "&cThat player does not have a team!");

						return;
					}
					if (!ot.getInvitedPlayers().contains(player.getUniqueId())) {

						Chat.getInstance().messagePlayer(player, "&cYou currently have no invitation to that team!");

						return;
					}
					if (ot.getMembers().size() >= 5) {

						Chat.getInstance().messagePlayer(player, "&cThat team already has 5 members!!");

						return;
					}
					ot.getInvitedPlayers().remove(player.getUniqueId());
					ot.getMembers().put(player.getUniqueId(), TeamRank.MEMBER);

					user.getTeam().messageMembers("&e" + player.getName() + " &ahas joined the team!");

					StatsManager.getInstance().refreshScoreBoard(player, false);

					Chat.getInstance().messagePlayer(player, "&aYou have joined team &b" + ot.getName());

					return;
				}
				if (user.getTeam() != null) {

					Chat.getInstance().messagePlayer(player, "&cYou currently are in a team!");

					return;
				}

				if (!team.getInvitedPlayers().contains(player.getUniqueId())) {

					Chat.getInstance().messagePlayer(player, "&cYou currently have no invitation to that team!");

					return;
				}
				team.getInvitedPlayers().remove(player.getUniqueId());
				team.getMembers().put(player.getUniqueId(), TeamRank.MEMBER);

				StatsManager.getInstance().refreshScoreBoard(player, false);

				Chat.getInstance().messagePlayer(player, "&aYou have joined team &b" + team.getName());

				user.getTeam().messageMembers("&e" + player.getName() + " &ahas joined the team!");

				return;
			}
			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " create <name> &e- Create your own new team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " teams &e- View all the teams on the server");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " disband &e- Disband your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " chat &e- Toggle to type to your team members");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " leave &e- Leave your currently team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " kick <player> &e-Kick a player from your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " join <name> &e-Join another user's team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " invite <player> &e- Invite a user to your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " uninvite <player> &e-Un-Invite a user to your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " setowner <name> &e- Give leadership of your team to another user");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " info &e- View information about your team");

			Chat.getInstance().messagePlayer(args.getSender(),
					"&a/" + args.getLabel() + " info <name> &e- View information on another team");

		}
	}
}
