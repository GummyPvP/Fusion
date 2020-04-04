package fusion.listeners;

import org.bukkit.entity.Player;
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
		
		Player player = event.getPlayer();
		
		if (player.isDead()) return;
		
		if (mKitUser.getInstance(player).hasKit()) return;
		
		Region newRegion = RegionManager.getInstance().getSmallestRegion(RegionManager.getInstance().getRegions(event.getTo().toVector()));

		if (newRegion == null) return;
		if (!(newRegion instanceof ProtectedRegion)) return;

		ProtectedRegion reg = (ProtectedRegion) newRegion;
		
		if (!reg.isPVPEnabled()) return;
		
		if (player.hasPermission("protectedregion.leave-nokit")) return;
		
		Spawn.getInstance().forceTP(player);
		
		Chat.getInstance().messagePlayer(player, "&cYou must pick a kit before exiting!");

	}

}
