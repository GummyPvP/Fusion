package fusion.events.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fusion.events.Event;
import fusion.events.events.arenas.LMSArena;
import fusion.events.utils.EventLeaveReason;
import fusion.events.utils.EventState;
import fusion.events.utils.EventType;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Created on Dec 23, 2016 by Jeremy Gooch.
	 * 
	 */

public class LMS extends Event implements Listener {
	
	private LMSArena arena;
	private String hostName;
	private EventState state = EventState.WAITING_FOR_PLAYERS;
	
	private List<String> players = new ArrayList<String>();
	
	public LMS(LMSArena arena, String hostName) {
		
		this.arena = arena;
		this.hostName = hostName;
		
	}
	
	@Override
	public String getName() {
		return "LMS";
	}

	@Override
	public List<String> getPlayers() {
		return players;
	}
	
	@Override
	public String getHostPlayerName() {
		return hostName;
	}

	@Override
	public int getNeededPlayers() {
		return 4;
	}

	@Override
	public int getMaxPlayers() {
		return 16;
	}

	@Override
	public Rank getRank() {
		return Rank.SLIME;
	}

	@Override
	public EventState getState() {
		return state;
	}
	
	@Override
	public void setState(EventState state) {
		this.state = state;
	}

	@Override 
	public EventType getType() {
		return EventType.LMS;
	}
	
	@Override
	public LMSArena getArena() {
		return arena;
	}
	
	@Override
	public void update() {
		
		if (getState() == EventState.STARTING) {
			
			
			
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		
		Player player = e.getEntity();
		
		if (!getPlayers().contains(player.getName())) return;
		
		removePlayer(player, EventLeaveReason.DEATH);
		
	}

}