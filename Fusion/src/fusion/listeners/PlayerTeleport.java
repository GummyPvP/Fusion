package fusion.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import fusion.kits.utils.kitutils.GladiatorManager;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.ProtectedRegion;
import fusion.utils.editing.regions.Region;
import fusion.utils.editing.regions.RegionManager;

public class PlayerTeleport implements Listener {

	@EventHandler
	public void onHit(PlayerTeleportEvent event) {
		
		Player player = event.getPlayer();
		
		if (event.getCause() == TeleportCause.ENDER_PEARL) {
			
			if (GladiatorManager.getInstance().getArena(player) != null) return; // don't let players TP away from a gladiator fight!
			
			Region newRegion = RegionManager.getInstance()
					.getSmallestRegion(RegionManager.getInstance().getRegions(event.getTo().toVector()));

			if (newRegion == null) return;
			if (!(newRegion instanceof ProtectedRegion)) return;
			
			ProtectedRegion reg = (ProtectedRegion) newRegion;
			
			if (!reg.isPVPEnabled()) {
				
				Chat.getInstance().messagePlayer(player, "&cYou may not teleport into safezone areas!");
				
				event.setCancelled(true);
				
				
				return;
			}
			
		}
		
	}
	
}
