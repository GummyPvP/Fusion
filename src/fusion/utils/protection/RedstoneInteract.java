package fusion.utils.protection;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RedstoneInteract implements Listener {
	
	@EventHandler
	public void onRedstoneInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (player.getGameMode() == GameMode.CREATIVE)
			return;
		
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		if (event.getClickedBlock().getType() == Material.REPEATER) {
			event.setCancelled(true);
		}
		
	}

}
