package fusion.utils.editing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import fusion.kits.utils.Kit;
import fusion.main.Fusion;
import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;
import fusion.utils.spawn.Spawn;

public class TeleportListener implements Listener {

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {

		Player player = event.getPlayer();
		mKitUser mkituser = mKitUser.getInstance(player);

		Region newRegion = RegionManager.getInstance()
				.getSmallestRegion(RegionManager.getInstance().getRegions(event.getTo().toVector()));

		if (!mkituser.hasKit())
			return;
		if (newRegion == null)
			return;

		if (newRegion instanceof ProtectedRegion) {

			ProtectedRegion reg = (ProtectedRegion) newRegion;

			Kit currentkit = mkituser.getKit();

			if (reg.getBlockedKits().contains(currentkit)) {

				Chat.getInstance().messagePlayer(player,
						String.format(Chat.BASE_COLOR + currentkit.getName() + " is blocked in this area!"));

				Bukkit.getServer().getScheduler().runTaskLater(Fusion.getInstance(), new Runnable() {
					public void run() {

						Spawn.getInstance().forceTP(player);
						Utils.get().sendTitle(player, 5, 40, 5, "&4&lYou can not be in this area with this kit!",
								"&cTeleported back to spawn!");

					}
				}, 40L);

			}

		}

	}

}
