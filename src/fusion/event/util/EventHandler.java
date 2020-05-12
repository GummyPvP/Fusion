package fusion.event.util;

import java.util.HashMap;
import java.util.Map;

import fusion.event.events.LMS;
import fusion.main.Fusion;
import fusion.utils.chat.Chat;

public class EventHandler {
	
	private FusionEvent currentEvent;
	
	private Map<String, FusionEvent> allEvents;
	
	private AnnouncementTask task;
	
	public EventHandler() {
		allEvents = new HashMap<String, FusionEvent>();
		populateEvents();
	}
	
	private void populateEvents() {
		allEvents.put("lms", new LMS());
	}
	
	public FusionEvent getEvent(String name) {
		return allEvents.get(name.toLowerCase());
	}
	
	public void setCurrentEvent(FusionEvent event) {
		this.currentEvent = event;
	}
	
	public FusionEvent getCurrentEvent() {
		return this.currentEvent;
	}
	
	public AnnouncementTask getAnnouncementTask() {
		return task;
	}
	
	public void runEvent(FusionEvent event) {
		
		setCurrentEvent(event);
		
		task = new AnnouncementTask(currentEvent);
		
		Chat.getInstance().broadcastMessage("&7[&eEvent&7] &3" + event.getName() + " &ais starting in &e60 seconds&a! Use /event join to participate!");
		
		task.runTaskTimer(Fusion.getInstance(), 0, 20);
	}
	
}


