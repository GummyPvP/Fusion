package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fusion.utils.multiplier.MultiplierManager;
import mpermissions.utils.events.PlayerPermissionChangeEvent;

public class PlayerUpdateRankEvent implements Listener {

	@EventHandler
	public void onRankUpdate(PlayerPermissionChangeEvent event) {
		
		MultiplierManager.getInstance().updateMultiplier(event.getUser().getPlayer());
		
	}
	
}
