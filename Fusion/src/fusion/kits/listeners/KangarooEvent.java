package fusion.kits.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
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

		if (e.getItem() == null) 
			return;
		
		if (e.getItem().getType() != Material.FIREWORK)
			return;
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.PHYSICAL)
			return;
		
		e.setCancelled(true);
		
		if (player.isFlying())
			return;
		
		if (midAir.contains(player.getName()))
			return;
		
		player.setFallDistance(-5F);
		
		Vector vector = player.getEyeLocation().getDirection();
		
		if (player.isSneaking()) { // more forward movement, rather than height
	        
	        vector.multiply(1.2F);
	        vector.setY(0.8D);
	        player.setVelocity(vector);
			
		} else {
		
	        vector.multiply(0.4F);
	        vector.setY(1.2D);
	        player.setVelocity(vector);
	        
		}
        
		midAir.add(player.getName());
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) { // Using this to check for onGround, as the method can be modified by hacked clients. Use with caution
		
		if (e.getTo().distanceSquared(e.getFrom()) == 0) return; // apparently more efficient to use distanceSquared
		
		Player player = e.getPlayer();
		
		Block underBlock = player.getLocation().subtract(0.0, 0.1, 0.0).getBlock();
		
		if (!midAir.contains(player.getName())) return;
		
		if (underBlock.getType() != Material.AIR && !underBlock.getType().isTransparent() && !underBlock.isLiquid()) { // if solid
			
			midAir.remove(player.getName());
			
		}
		
	}
	
}
