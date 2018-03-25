package fusion.kits.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import fusion.kits.utils.KitManager;

/**
	 * 
	 * Created on Dec 17, 2016 by Jeremy Gooch.
	 * 
	 */

public class KangarooEvent implements Listener {
	
	private List<String> midAir = new ArrayList<String>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Kangaroo")))
			return;

		if (e.getItem().getType() != Material.FIREWORK)
			return;
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.PHYSICAL)
			return;
		
		if (player.isFlying())
			return;
		
		if (midAir.contains(player.getName()))
			return;
		
		e.setCancelled(true);
		
		player.setFallDistance(-0.5F);
		
		midAir.add(player.getName());
		
		if (player.isSneaking()) { // more forward movement, rather than height
			
	        Vector vector = player.getEyeLocation().getDirection();
	        vector.multiply(0.6F);
	        vector.setY(0.8D);
	        player.setVelocity(vector);
			
			return;
		}
		
        Vector vector = player.getEyeLocation().getDirection();
        vector.multiply(0.4F);
        vector.setY(1.2D);
        player.setVelocity(vector);
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) { // Using this to check for onGround, as the method can be modified by hacked clients. Use with caution
		
		if (e.getTo().distanceSquared(e.getFrom()) == 0) return; // apparently more efficient to use distanceSquared
		
		Player player = e.getPlayer();
		
		if (!player.getLocation().subtract(0.0, 0.1, 0.0).getBlock().isLiquid()) { // if solid
			
			if (midAir.contains(player.getName())) {
				midAir.remove(player.getName());
			}
			
		}
		
	}
	
}
