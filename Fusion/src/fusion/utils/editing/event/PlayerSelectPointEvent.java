package fusion.utils.editing.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import fusion.utils.editing.EditorSession;

public final class PlayerSelectPointEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	EditorSession session;
	
	Action action;
	
	Vector point;
	
	public PlayerSelectPointEvent(EditorSession session, Vector point, Action action) {
		this.session = session;
		this.action = action;
		this.point = point;
	}
	
	public EditorSession getSession() {
		return session;
	}
	
	public Action getAction() {
		return action;
	}
	
	public Vector getPoint() {
		return point;
	}		

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}