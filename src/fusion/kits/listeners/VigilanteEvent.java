package fusion.kits.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.RegionManager;

public class VigilanteEvent implements Listener {

	
	 // x = 406.901 y =  72 z = 1196.976
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = (Player) e.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(e.getPlayer(), KitManager.getInstance().valueOf("Vigilante")))
			return;


		if (e.getPlayer().getInventory().getItemInMainHand().getType()  == null || e.getPlayer().getInventory().getItemInMainHand() == null) 
			return;

		if (RegionManager.getInstance().isInProtectedRegion(e.getPlayer()))
			
			return;
		if (mKitUser.getInstance(p).isInGladiatorArena())
			return;
		
		if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GUNPOWDER) {
			
			e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			e.getPlayer().updateInventory();
			
			Location location;
			World world;
			double x, y, z;
			int yaw, pitch;
			
			world = Bukkit.getWorld(Fusion.getInstance().getKitInfoFile().getString("vigilante.world"));
			x = Fusion.getInstance().getKitInfoFile().getDouble("vigilante.x");
			y = Fusion.getInstance().getKitInfoFile().getDouble("vigilante.y");
			z = Fusion.getInstance().getKitInfoFile().getDouble("vigilante.z");
			yaw = Fusion.getInstance().getKitInfoFile().getInt("vigilante.yaw");
			pitch = Fusion.getInstance().getKitInfoFile().getInt("vigilante.pitch");
			location = new Location(world, x, y, z, yaw, pitch);
			
			e.getPlayer().teleport(location);
			
			Chat.getInstance().messagePlayer(p, "&aYou've been teleported to the Hideout!");
			Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Vigilante "+ p.getName() + " has hid away to avoid execution!");
			
		}
		
	}
}
