package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.ProtectedRegion;
import fusion.utils.editing.regions.Region;
import fusion.utils.editing.regions.RegionManager;
import fusion.utils.spawn.Spawn;

public class PlayerMove implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent event) {

		if (!mKitUser.getInstance(event.getPlayer()).hasKit()) {

			Region newRegion = RegionManager.getInstance()
					.getSmallestRegion(RegionManager.getInstance().getRegions(event.getTo().toVector()));

			if (newRegion == null) return;
			if (!(newRegion instanceof ProtectedRegion)) return;
			
			ProtectedRegion reg = (ProtectedRegion) newRegion;
			
			if (reg.isPVPEnabled()) {

				if (!event.getPlayer().hasPermission("protectedregion.leave-nokit")) {

					Spawn.getInstance().teleport(event.getPlayer());

					Chat.getInstance().messagePlayer(event.getPlayer(), "&cYou must pick a kit before exiting!");

					return;

				}

			}
		}
	}

}
