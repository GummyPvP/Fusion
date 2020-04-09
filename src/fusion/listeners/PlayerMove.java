package fusion.listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

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
	
	@EventHandler
	public void onSponge(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		if (player.getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.SPONGE) {
			
			int wool = countWool(player.getWorld(), player.getLocation().subtract(0.0, 1.0, 0.0).toVector());
			
			player.setVelocity(new Vector(0.0, 1.0 + wool, 0.0));
		}
		
	}
	
	private int countWool(World world, Vector vector) {
		
		int result = 0;
		
		Vector currentVector = vector;
		
		while (currentVector.toLocation(world).subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.WHITE_WOOL) {
			result++;
			currentVector.subtract(new Vector(0, 1, 0));
		}
		
		return result;
		
	}

}
