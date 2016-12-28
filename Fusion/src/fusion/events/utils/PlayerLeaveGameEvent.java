package fusion.events.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import fusion.events.Event;

/**
	 * 
	 * Created on Dec 24, 2016 by Jeremy Gooch.
	 * 
	 */

public class PlayerLeaveGameEvent extends org.bukkit.event.Event {
	
	private Player player;
	private Event event;
	private EventLeaveReason reason;
	
	public PlayerLeaveGameEvent(Player player, Event event, EventLeaveReason reason) {
		
		this.player = player;
		this.event = event;
		this.reason = reason;
		
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
	
	public EventLeaveReason getReason() {
		return reason;
	}
	
}
