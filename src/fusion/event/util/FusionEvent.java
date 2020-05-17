package fusion.event.util;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fusion.main.Fusion;
import fusion.utils.Utils;
import fusion.utils.chat.Chat;
import fusion.utils.spawn.Spawn;

public abstract class FusionEvent {
	
	public abstract String getName();
	
	public abstract int getMinimumPlayerCount();
	
	public abstract int getMaximumPlayerCount();
	
	public abstract Set<Player> getPlayers();
	
	public abstract BukkitTask getTaskTimer();
	
	public abstract void setStarted(boolean started);
	
	public abstract boolean isStarted();
	
	public void addPlayer(Player player) {
		
		if (isStarted()) return;
		
		getPlayers().add(player);
		messageParticipants("&e" + player.getName() + " &ahas joined the event! &6(" + getPlayers().size() + "/" + (getMaximumPlayerCount() == -1 ? Utils.getInfinitySymbol() : getMaximumPlayerCount()) + ")");
	}
	
	public void removePlayer(Player player) {
		
		getPlayers().remove(player);
		
		if (!isStarted()) {
			messageParticipants("&e" + player.getName() + " &ahas left the event! &6(" + getPlayers().size() + "/" + (getMaximumPlayerCount() == -1 ? Utils.getInfinitySymbol() : getMaximumPlayerCount()) + ")");
		}
	}
	
	public void messageParticipants(String message) {
		
		for (Player player : getPlayers()) {
			Chat.getInstance().messagePlayer(player, "&7[&6" + getName() + "&7] " + message);
		}
		
	}
	
	public abstract void start();

	public void end() {
		
		for (Player player : getPlayers()) {
			Spawn.getInstance().forceTP(player);
		}
		
		getPlayers().clear();
		Fusion.getInstance().getEventHandler().setCurrentEvent(null);
		setStarted(false);
	}
	
}
