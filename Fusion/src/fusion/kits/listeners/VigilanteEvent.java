package fusion.kits.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fusion.kits.utils.KitManager;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.protection.RegionManager;

public class VigilanteEvent implements Listener {

	
	 // x = 406.901 y =  72 z = 1196.976
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = (Player) e.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(e.getPlayer(), KitManager.getInstance().valueOf("Vigilante")))
			return;


		if (e.getPlayer().getItemInHand().getType()  == null || e.getPlayer().getItemInHand() == null) 
			return;

		if (RegionManager.getInstance().isInProtectedRegion(e.getPlayer()))
			
			return;
		if (mKitUser.getInstance(p).isGlad())
			return;
		
		if (e.getPlayer().getItemInHand().getType() == Material.SULPHUR) {
			
			e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			e.getPlayer().updateInventory();
			
			Location location = new Location(Bukkit.getServer().getWorld("NightskyKitpvp"), 406.904, 72, 1196.976);
			
			e.getPlayer().teleport(location);
			
			Chat.getInstance().messagePlayer(p, "&aYou've been teleported to the Hideout!");
			Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Vigilante "+ p.getName() + " has hid away to avoid arrest!");
			
		}
		
	}
}
