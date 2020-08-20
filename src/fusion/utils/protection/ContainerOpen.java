package fusion.utils.protection;

import org.bukkit.GameMode;
import org.bukkit.block.Barrel;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class ContainerOpen implements Listener {
	
	@EventHandler
	public void onContainerOpen(InventoryOpenEvent event) {
		
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		
		if (event.getInventory().getHolder() instanceof Chest || event.getInventory().getHolder() instanceof DoubleChest || event.getInventory().getHolder() instanceof Barrel || event.getInventory().getHolder() instanceof Hopper || event.getInventory().getHolder() instanceof Furnace || event.getInventory().getHolder() instanceof Dispenser) {
			event.setCancelled(true);
		}
		
	}

}
