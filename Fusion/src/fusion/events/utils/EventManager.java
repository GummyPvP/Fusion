package fusion.events.utils;

import java.util.HashSet;
import java.util.Set;

import fusion.events.lms.LMS;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 4, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class EventManager {
	
	private EventManager() { }
	
	private static EventManager instance = new EventManager();
	
	public static EventManager getInstance() {
		return instance;
	}
	
	private Set<Event> events = new HashSet<Event>();
	
	public void registerEvent(Event event) {
		
		events.add(event);
		event.register();
		
	}
	
	public void registerEvents() {
		
		registerEvent(LMS.getInstance());
		
	}

}
