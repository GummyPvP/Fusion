package fusion.events;

import java.util.List;

import fusion.events.utils.Arena;
import fusion.events.utils.EventState;
import fusion.events.utils.EventType;
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
	
	public abstract void update();
	
}
