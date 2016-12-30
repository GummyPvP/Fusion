package fusion.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import fusion.events.utils.EventState;
import fusion.events.utils.PlayerJoinGameEvent;
import fusion.events.utils.PlayerLeaveGameEvent;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;
import fusion.utils.spawn.Spawn;

/**
 * 
 * Created on Dec 23, 2016 by Jeremy Gooch.
 * 
 */

public class EventManager implements Listener {

	private EventManager() { }

	private static EventManager instance = new EventManager();

	public static EventManager get() {
		return instance;
	}
	
	private BukkitTask task;

	private static int MAX_EVENTS = 2;

	private List<Event> activeEvents = new ArrayList<Event>(MAX_EVENTS);
	private List<Event> queue = new ArrayList<Event>();
	
	public List<Event> getActiveEvents() {
		return activeEvents;
	}
	
	public List<Event> getEventQueue() {
		return queue;
	}
	
	private void update() {
		
		if (activeEvents.size() < MAX_EVENTS) {
			
			if (!queue.isEmpty()) {
			
				activeEvents.add(queue.get(0));
			
				queue.remove(0);
			}
			
		}
		
		for (Event event : activeEvents) {
			
			event.update();
			
			if (event.getState() != EventState.FINISHED)
				continue;
			
			// event should be done and ready to be removed
			
			for (String name : event.getPlayers()) {
				
				Player player = Bukkit.getPlayer(name);
				
				if (player == null) continue;
				
				Spawn.getInstance().forceTP(player);
				
			}
			
			activeEvents.remove(event);
			
		}
		
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinGameEvent e) {
		
		if (!activeEvents.contains(e.getEvent()))
			return;
		
		Event event = e.getEvent();
		
		if (event.getAmountOfPlayers() >= event.getMaxPlayers()) {
			
			e.setCancelled(true);
			
		}
		
		if (e.isCancelled()) {
			
			Chat.getInstance().messagePlayer(e.getPlayer(), "&cSorry, you are not able to join this event right now");
			
			return;
		}
		
		Chat.getInstance().broadcastMessage("&a" + e.getPlayer().getName() + " joined the " + event.getName() + " event! &7(&b" + event.getAmountOfPlayers() + "&7/&b" + event.getMaxPlayers() + "&7)");
		Chat.getInstance().broadcastMessage("&6Type /event join to play too!");
		
		if (event.getAmountOfPlayers() >= event.getNeededPlayers()) {
			
			event.setState(EventState.STARTING);
			
			return;
		}
		
	}
	
	@EventHandler
	public void onPlayerLeaveEvent(PlayerLeaveGameEvent e) {
		
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
	
	public void start() {
		
		this.task = Bukkit.getScheduler().runTaskTimer(Fusion.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				update();
				
			}
		}, 0L, 20L);
		
	}
	
	public void stop() {
		
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		
	}
}
