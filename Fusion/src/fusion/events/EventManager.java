package fusion.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fusion.events.utils.EventState;
import fusion.events.utils.PlayerJoinGameEvent;
import fusion.events.utils.PlayerLeaveGameEvent;
import fusion.utils.chat.Chat;

/**
 * 
 * Created on Dec 23, 2016 by Jeremy Gooch.
 * 
 */

public class EventManager implements Listener {

	private EventManager() {
	}

	private static EventManager instance = new EventManager();

	public static EventManager get() {
		return instance;
	}

	private static int MAX_EVENTS = 2;

	private List<Event> activeEvents = new ArrayList<Event>();
	private List<Event> queue = new ArrayList<Event>();

	private void update() {

		if (activeEvents.size() < MAX_EVENTS) {

			if (queue.isEmpty())
				return;

			activeEvents.add(queue.get(0));

			queue.remove(0);

		}
		
		for (Event event : activeEvents) {
			
			event.update();
			
			if (event.getState() != EventState.FINISHED)
				return;
			
			// event should be done and ready to be removed

		}

	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinGameEvent e) {
		
		if (!activeEvents.contains(e.getEvent()))
			return;
		
		Event event = e.getEvent();
		
		if (event.getPlayers().size() >= event.getNeededPlayers()) {
			
			// wait like 10 seconds and check again
			
			return;
		}
		
		if (event.getState() != EventState.WAITING_FOR_PLAYERS)
			return;

		Chat.getInstance().broadcastMessage("&6" + e.getPlayer().getName() + " &ajoined the &6" + event.getName()
				+ " event&a! (&b" + event.getAmountOfPlayers() + "&6/&b" + event.getMaxPlayers() + ")");

	}
	
	@EventHandler
	public void onPlayerLeave(PlayerLeaveGameEvent e) {
		
		Player player = e.getPlayer();
		Event event = e.getEvent();
		
		switch (e.getReason()) {
		
		case DEATH:
			
			event.messagePlayers(player.getName() + " died and has lost the event!");
			
			break;
			
		default:
			
			event.messagePlayers(player.getName() + " left and lost the event!");
			
			break;
		}
		
	}
}
