package fusion.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fusion.listeners.CombatLog;

public class StatsManager {

	public StatsManager() {

	}

	static StatsManager instance = new StatsManager();

	public static StatsManager getInstance() {

		return instance;

	}

	HashMap<UUID, Integer> kills = new HashMap<UUID, Integer>();
	HashMap<UUID, Integer> deaths = new HashMap<UUID, Integer>();
	HashMap<UUID, Integer> score = new HashMap<UUID, Integer>();

	Plugin p;

	public void setup(Plugin plugin) {

		p = plugin;

	}

	ChatColor currentcolor = null;


	public void startScoreboard(Plugin plugin) {
		
		currentcolor = ChatColor.YELLOW;

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {

				if (currentcolor == ChatColor.YELLOW) {
					currentcolor = ChatColor.GREEN;
				} else {
					currentcolor = ChatColor.YELLOW;
				}

				for (Player on : Bukkit.getOnlinePlayers()) {

					if (on != null) {

						refreshScoreBoard(on, CombatLog.getInstance().isInCombat(on));

					}

				}

			}
		}, 0L, 20L);

	}

	public void refreshScoreBoard(Player p, boolean ct) {

//		if (ct) {
//
//			mKitUser player = mKitUser.getInstance(p);
//
//			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
//			Objective o = board.registerNewObjective("normal", "dummy");
//
//			o.setDisplayName(ChatColor.translateAlternateColorCodes('&', "  &b&lGummyPvP  "));
//			o.setDisplaySlot(DisplaySlot.SIDEBAR);
//
//			Score line1 = o.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "--------");
//			Score kit = o.getScore(currentcolor + "Kit: " + ChatColor.WHITE
//					+ (player.hasKit() ? player.getKit().getName() : "none"));
//			Score kills = o.getScore(currentcolor + "Kills: " + ChatColor.WHITE + player.getKills());
//			Score deaths = o.getScore(currentcolor + "Deaths: " + ChatColor.WHITE + player.getDeaths());
//			Score team = o.getScore(currentcolor + "Team:");
//			Score t = o.getScore(player.getTeam() != null ? player.getTeam().getName() : ChatColor.WHITE + "none");
//
//			Score emp = o.getScore(ChatColor.translateAlternateColorCodes('&', "  &8&m-----  "));
//
//			Score combattag = o.getScore(
//					currentcolor + "CombatTag: " + ChatColor.WHITE + CombatLog.getInstance().getRemainingTime(p));
//
//			line1.setScore(15);
//			kit.setScore(14);
//			kills.setScore(13);
//			deaths.setScore(12);
//			team.setScore(11);
//			t.setScore(10);
//
//			emp.setScore(9);
//			combattag.setScore(8);
//
//			p.setScoreboard(board);
//
//			return;
//		}
//
//		mKitUser player = mKitUser.getInstance(p);
//
//		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
//		Objective o = board.registerNewObjective("normal", "dummy");
//
//		o.setDisplayName(ChatColor.translateAlternateColorCodes('&', "  &b&lGummyPvP  "));
//		o.setDisplaySlot(DisplaySlot.SIDEBAR);
//
//		Score line1 = o.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "--------");
//		Score kit = o.getScore(
//				currentcolor + "Kit: " + ChatColor.WHITE + (player.hasKit() ? player.getKit().getName() : "none"));
//		Score kills = o.getScore(currentcolor + "Kills: " + ChatColor.WHITE + player.getKills());
//		Score deaths = o.getScore(currentcolor + "Deaths: " + ChatColor.WHITE + player.getDeaths());
//		Score team = o.getScore(currentcolor + "Team:");
//		Score t = o.getScore(player.getTeam() != null ? player.getTeam().getName() : ChatColor.WHITE + "none");
//
//		line1.setScore(15);
//		kit.setScore(14);
//		kills.setScore(13);
//		deaths.setScore(12);
//		team.setScore(11);
//		t.setScore(10);
//
//		p.setScoreboard(board);
	}

}
