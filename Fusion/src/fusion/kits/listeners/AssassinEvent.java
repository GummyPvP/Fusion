package fusion.kits.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import fusion.kits.utils.KitManager;
import fusion.kits.utils.kitutils.GladiatorManager;
import fusion.utils.editing.regions.RegionManager;

/**
	 * 
	 * Created on Apr 2, 2018 by Jeremy Gooch.
	 * 
	 */

public class AssassinEvent implements Listener {
	
	private Set<String> assassins = new HashSet<String>();
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(e.getPlayer(), KitManager.getInstance().valueOf("Assassin")))
			return;

		if (player.getItemInHand().getType() != Material.NETHER_STAR)
			return;

		if (RegionManager.getInstance().isInProtectedRegion(player))
			return;
		
		if (GladiatorManager.getInstance().getArena(player) != null)
			return;
		
		if (assassins.contains(player.getName()))
			return;
		
		
		
	}
	
	private void assassin(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		//player.addPotionEffect(arg0)
	}

}
