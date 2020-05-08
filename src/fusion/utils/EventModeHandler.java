package fusion.utils;

import java.util.HashSet;
import java.util.Set;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;

public class EventModeHandler {
	
	private boolean inEventMode;
	
	private Set<Kit> allowedKits;
	
	private long timeLastStarted;
	
	public EventModeHandler() {
		this.inEventMode = false;
		this.allowedKits = new HashSet<Kit>();
		this.allowedKits.addAll(KitManager.getInstance().getKits());
		this.timeLastStarted = 0L;
	}
	
	public boolean isInEventMode() {
		return inEventMode;
	}
	
	public void setInEventMode(boolean eventMode) {
		this.inEventMode = eventMode;
		
		if (eventMode) {
			timeLastStarted = System.currentTimeMillis();
		} else timeLastStarted = 0L;
	}
	
	public Set<Kit> getAllowedKits() {
		return allowedKits;
	}
	
	public void addAllowedKit(Kit kit) {
		this.allowedKits.add(kit);
	}
	
	public void removeAllowedKit(Kit kit) {
		this.allowedKits.remove(kit);
	}

	public void setTimeLastStarted(long time) {
		this.timeLastStarted = time;
	}
	
	public long getTimeLastStarted() {
		return this.timeLastStarted;
	}
	
}
