package fusion.utils.protection;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class HangingBreak implements Listener {
	
	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		
		if (!(event.getRemover() instanceof Player)) return;
		
		Player player = (Player) event.getRemover();
		
		if (player.getGameMode() == GameMode.CREATIVE)
			return;

		event.setCancelled(true);
		
	}

}
