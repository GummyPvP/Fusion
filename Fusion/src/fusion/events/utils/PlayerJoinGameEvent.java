package fusion.events.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import fusion.events.Event;

/**
	 * 
	 * Created on Dec 24, 2016 by Jeremy Gooch.
	 * 
	 */

public class PlayerJoinGameEvent extends org.bukkit.event.Event implements Cancellable {
	
	private Player player;
	private Event event;
	private boolean cancelled;
	
	public PlayerJoinGameEvent(Player player, Event event) {
		
		this.player = player;
		this.event = event;
		
	}
	
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Event getEvent() {
		return event;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}

}
