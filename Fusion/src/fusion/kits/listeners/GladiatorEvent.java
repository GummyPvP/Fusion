package fusion.kits.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fusion.kits.utils.KitManager;
import fusion.kits.utils.kitutils.GladiatorArena;
import fusion.kits.utils.kitutils.GladiatorManager;
import fusion.utils.editing.regions.RegionManager;

/*
 * 
 * Created on Dec 4, 2016 by Jeremy Gooch.
 * 
 */

public class GladiatorEvent implements Listener {

	@EventHandler
	public void onPlayerInteractWithEntity(PlayerInteractEntityEvent e) {

		if (!KitManager.getInstance().hasRequiredKit(e.getPlayer(), KitManager.getInstance().valueOf("Gladiator")))
			return;

		if (!(e.getRightClicked() instanceof Player))
			return;

		if (e.getPlayer().getItemInHand().getType() != Material.IRON_FENCE)
			return;

		if (RegionManager.getInstance().isInProtectedRegion(e.getPlayer()))
			return;
		
		if (GladiatorManager.getInstance().getArena(e.getPlayer()) != null)
			return;
		
		e.setCancelled(true);

		Player player = e.getPlayer();
		Player attacked = (Player) e.getRightClicked();

		if (attacked.getGameMode() == GameMode.CREATIVE)
			return;

		GladiatorManager.getInstance().startFight(player, attacked);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {

		Player player = e.getEntity();
		
		if (GladiatorManager.getInstance().getArena(player) == null) return;
		
		GladiatorManager.getInstance().endFight(player.getKiller());

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {

		Player player = e.getPlayer();
		
		GladiatorArena arena = GladiatorManager.getInstance().getArena(player);
		
		if (GladiatorManager.getInstance().getArena(player) == null) return;
		
		if (arena.getAttacked() == player) {
			GladiatorManager.getInstance().endFight(arena.getAttacker());
		} else GladiatorManager.getInstance().endFight(arena.getAttacked());
		
	}
}
