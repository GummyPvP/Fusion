package fusion.utils.protection;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class WaterPlace implements Listener {
	
	@EventHandler
	public void onLiquidPlace(PlayerBucketEmptyEvent event) {
		
		Player player = event.getPlayer();
		
		if (player.getGameMode() == GameMode.CREATIVE) return;
		
		event.setCancelled(true);
		
	}

}
