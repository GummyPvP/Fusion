package fusion.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import fusion.utils.chat.Chat;
import fusion.utils.protection.ProtectedRegion;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class EntityDamageByEntity implements Listener {
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
		
		Entity entityReciever = e.getEntity();
		
		Entity entityHitter = e.getDamager();
		
		if (!(entityHitter instanceof Player)) return;
		
		Player hitter = (Player) entityHitter;
		
		if (hitter.getItemInHand() == null) return;
		
		if (hitter.getItemInHand().getType().toString().contains("SWORD") || hitter.getItemInHand().getType().toString().contains("AXE")) {
			
			hitter.getItemInHand().setDurability((short) -1);
			
		}
		
		if (!(entityReciever instanceof Player)) return;
		
		Player reciever = (Player) entityReciever;
		
		for (ItemStack armor : reciever.getInventory().getArmorContents()) {
			
			armor.setDurability((short) -1);
			
		}
		
		Region hitterRegion = RegionManager.getInstance().getSmallestRegion(RegionManager.getInstance().getRegions(hitter.getLocation().toVector()));
		Region recieverRegion = RegionManager.getInstance().getSmallestRegion(RegionManager.getInstance().getRegions(reciever.getLocation().toVector()));
		
		if (hitterRegion instanceof ProtectedRegion && recieverRegion instanceof ProtectedRegion) {
			
			if (!((ProtectedRegion) hitterRegion).isPVPEnabled() && !((ProtectedRegion) recieverRegion).isPVPEnabled()) return;
			
		}
		
		if (hitter.isFlying() || reciever.isFlying()) {
			
			e.setCancelled(true);
			return;
		}
		
		if (!CombatLog.getInstance().isInCombat(hitter)) {
			
			Chat.getInstance().messagePlayer(hitter, Chat.IMPORTANT_COLOR + "You are now in combat! Do not log out!");
			
			CombatLog.getInstance().startTimer(hitter);
			
		}
		
		if (!CombatLog.getInstance().isInCombat(reciever)) {
			
			Chat.getInstance().messagePlayer(reciever, Chat.IMPORTANT_COLOR + "You are now in combat! Do not log out!");
			
			CombatLog.getInstance().startTimer(reciever);
			
		}
		
		CombatLog.getInstance().resetTimer(hitter);
		CombatLog.getInstance().resetTimer(reciever);
	}

}
