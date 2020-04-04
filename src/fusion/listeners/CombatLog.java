package fusion.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import fusion.main.Fusion;
import fusion.utils.chat.Chat;

/**
	 * 
	 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class CombatLog {
	
	private CombatLog() { }
	
	private static CombatLog instance = new CombatLog();
	
	public static CombatLog getInstance() {
		return instance;
	}
	
	private Map<String, Integer> combatScheduler = new HashMap<String, Integer>();
	private Map<String, Integer> combatHandler = new HashMap<String, Integer>();
	
	private static final int COMBAT_TIME = 30;
	
	public void startTimer(final Player player) {
		
		combatHandler.put(player.getName(), COMBAT_TIME);
		
		combatScheduler.put(player.getName(), Bukkit.getScheduler().scheduleSyncRepeatingTask(Fusion.getInstance(), new Runnable() {

			@Override
			public void run() {
				
				if (isInCombat(player) && getRemainingTime(player) <= 1) {
					
					remove(player);
					
					//player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					
					//StatsManager.getInstance().refreshScoreBoard(player, false);
					
				} else {
					
					combatHandler.replace(player.getName(), getRemainingTime(player), getRemainingTime(player) - 1);
					
					//StatsManager.getInstance().refreshScoreBoard(player, true);
					
				}
				
			}
			
		}, 0L, 20L));
		
	}
	
	public void resetTimer(Player player) {
		if (!isInCombat(player)) return;
		combatHandler.replace(player.getName(), getRemainingTime(player), COMBAT_TIME);
	}
	
	public boolean isInCombat(Player player) {
		return combatHandler.containsKey(player.getName());
	}
	
	public int getRemainingTime(Player player) {
		
		if (!isInCombat(player)) return -1;
		
		return combatHandler.get(player.getName());
		
	}
	
	public void remove(Player player) {
		combatHandler.remove(player.getName());
		
		if (combatScheduler.containsKey(player.getName())) Bukkit.getScheduler().cancelTask(combatScheduler.get(player.getName()));
		
		combatScheduler.remove(player.getName());
		
		if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null && player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("combatlog")) {
			
			player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			
		}
		
		Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You are no longer in combat!");
	}
	
	public void debug() {
		
		Bukkit.broadcastMessage(combatHandler.toString());
		Bukkit.broadcastMessage(combatScheduler.toString());
		
	}
	
	public void refreshScoreboard(Player player) {
		
//		String correctSecond = getRemainingTime(player) == 1 ? "second" : "seconds";
//		
//		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
//		Objective o = board.registerNewObjective("combatlog", "dummy");
//		o.setDisplayName(Chat.SECONDARY_BASE + "Combat Timer");
//		o.setDisplaySlot(DisplaySlot.SIDEBAR);
//		
//		Score timerString = o.getScore(ChatColor.YELLOW + "Time Left:");
//		Score realTime = o.getScore("  " + ChatColor.GOLD + CombatLog.getInstance().getRemainingTime(player) + " " + correctSecond);
//		
//		timerString.setScore(15);
//		realTime.setScore(14);
//		
//		player.setScoreboard(board);
//		
//		StatsManager.getInstance().refreshScoreBoard(player, isInCombat(player));
		
	}
	
}
