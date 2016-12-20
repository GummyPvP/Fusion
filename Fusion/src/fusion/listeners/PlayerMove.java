package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;
import fusion.utils.spawn.Spawn;
import klap.utils.mPlayer;
import mpermissions.utils.permissions.Rank;

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

				if (!mPlayer.getInstance(event.getPlayer()).getGroup().getRank().hasRequiredRank(Rank.MODERATOR)) {

					Spawn.getInstance().teleport(event.getPlayer());

					Chat.getInstance().messagePlayer(event.getPlayer(), "&cYou must pick a kit before exiting!");

					return;

				}

			}
		}
	}

}
