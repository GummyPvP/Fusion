package fusion.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.events.utils.Arena;
import fusion.events.utils.EventLeaveReason;
import fusion.events.utils.EventState;
import fusion.events.utils.EventType;
import fusion.events.utils.PlayerJoinGameEvent;
import fusion.events.utils.PlayerLeaveGameEvent;
import fusion.utils.chat.Chat;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Created on Dec 23, 2016 by Jeremy Gooch.
	 * 
	 */

public abstract class Event {
	
	public abstract String getName();
	
	public abstract List<String> getPlayers();
	
	public abstract String getHostPlayerName();
	
	public abstract int getNeededPlayers();
	
	public abstract int getMaxPlayers();
	
	public abstract Rank getRank();
	
	public abstract EventState getState();
	
	public abstract EventType getType();
	
	public abstract void setState(EventState state);
	
	public abstract Arena getArena();
	
	public abstract ItemStack getItemGUI();
	
	public abstract void update();
	
	public int getAmountOfPlayers() {
		return getPlayers().size();
	}
	
	public void addPlayer(Player player){
		getPlayers().add(player.getName());
		Bukkit.getPluginManager().callEvent(new PlayerJoinGameEvent(player, this));
	}
	
	public void removePlayer(Player player, EventLeaveReason reason) {
		getPlayers().remove(player.getName());
		Bukkit.getPluginManager().callEvent(new PlayerLeaveGameEvent(player, this, reason));
	}
	
	public boolean isJoinable() {
		return getAmountOfPlayers() < getMaxPlayers() || getState() == EventState.WAITING_FOR_PLAYERS || getState() == EventState.STARTING;
	}
	
	public void messagePlayers(String message) {
		
		for (String name : getPlayers()) {
			
			Player player = Bukkit.getPlayer(name);
			
			if (player == null) continue;
			
			Chat.getInstance().messagePlayer(player, "&6[&a" + getName() + "&6] &e" + message);
			
		}
		
	}
}
