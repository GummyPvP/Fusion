package fusion.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fusion.main.Main;
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
	
	private static final int COMBAT_TIME = 15;
	
	public void startTimer(final Player player) {
		
		combatHandler.put(player.getName(), COMBAT_TIME);
		
		combatScheduler.put(player.getName(), Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				
				if (isInCombat(player) && getRemainingTime(player) <= 1) {
					
					remove(player);
					Bukkit.getScheduler().cancelTask(combatScheduler.get(player.getName()));
					
				} else combatHandler.replace(player.getName(), getRemainingTime(player), getRemainingTime(player) - 1);
				
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
		Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You are no longer in combat!");
	}
}
