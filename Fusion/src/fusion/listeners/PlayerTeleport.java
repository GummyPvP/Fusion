package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import fusion.utils.chat.Chat;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;

public class PlayerTeleport implements Listener {

	@EventHandler
	public void onHit(PlayerTeleportEvent event) {
		
		if (event.getCause() == TeleportCause.ENDER_PEARL) {
			
			Region newRegion = RegionManager.getInstance()
					.getSmallestRegion(RegionManager.getInstance().getRegions(event.getTo().toVector()));

			if (newRegion == null) return;
			if (!(newRegion instanceof ProtectedRegion)) return;
			
			ProtectedRegion reg = (ProtectedRegion) newRegion;
			
			if (!reg.isPVPEnabled()) {
				
				Chat.getInstance().messagePlayer(event.getPlayer(), "&cYou can not enderpearl into safezone areas!");
				
				event.setCancelled(true);
				
				
				return;
			}
			
		}
		
	}
	
}
