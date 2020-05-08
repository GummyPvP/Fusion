package fusion.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;

public class EventModeHandler {
	
	private boolean inEventMode;
	
	private Set<Kit> allowedKits;
	
	private Map<String, ItemStack[]> customKits;
	
	private long timeLastStarted;
	
	public EventModeHandler() {
		this.inEventMode = false;
		this.allowedKits = new HashSet<Kit>();
		this.allowedKits.addAll(KitManager.getInstance().getKits());
		this.setCustomKits(new HashMap<String, ItemStack[]>()); 
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

	public Map<String, ItemStack[]> getCustomKits() {
		return customKits;
	}

	public void setCustomKits(Map<String, ItemStack[]> customKits) {
		this.customKits = customKits;
	}
	
	public void addCustomKit(String name, ItemStack[] contents) {
		this.customKits.put(name, contents);
	}
	
	public void removeCustomKit(String name) {
		this.customKits.remove(name);
	}
	
	public ItemStack[] getCustomKit(String name) {
		return this.customKits.get(name);
	}
	
	public void applyCustomKit(Player player, String kit) {
		ItemStack[] contents = getCustomKit(kit);
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		player.getInventory().setContents(contents);
		player.updateInventory();
	}
	
}
