package fusion.utils.protection;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class HangingPlace implements Listener {
	
	@EventHandler
	public void onHangingPlace(HangingPlaceEvent event) {
		
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;

		event.setCancelled(true);
		
	}

}
