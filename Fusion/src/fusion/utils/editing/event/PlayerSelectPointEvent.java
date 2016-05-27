package fusion.utils.editing.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import fusion.utils.editing.EditorSession;

public final class PlayerSelectPointEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	Player player;
	
	EditorSession session;
	
	Action action;
	
	Vector point;
	
	public PlayerSelectPointEvent(Player player, EditorSession session, Vector point, Action action) {
		this.player = player;
		this.session = session;
		this.action = action;
	}

	public Player getPlayer() {
		return player;
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