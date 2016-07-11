package fusion.listeners;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fusion.utils.ItemBuilder;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.gui.KitGUI;
import fusion.utils.gui.ShopGUI;
import fusion.utils.gui.WarpGUI;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;
import klap.utils.mPlayer;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 20, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PlayerInteract implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		
		if (item == null) return;
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			
			switch (item.getType()) {
			case MUSHROOM_SOUP:
				
				if (player.getHealth() >= player.getMaxHealth()) return;
				
				e.setCancelled(true);
				
				player.setHealth(player.getHealth() + 7 >= player.getMaxHealth() ? player.getMaxHealth() : player.getHealth() + 7);
				
				ItemStack bowl = new ItemBuilder(Material.BOWL).name("&bBowl").lore(Arrays.asList("A bowl is most useful when it's empty.", "Hah! Just kidding! You're gonna die.")).build();
				
				player.setItemInHand(bowl);
				
				player.playSound(player.getLocation(), Sound.BURP, 1, 1);
				
				player.updateInventory();
				
				break;
				
			case NETHER_STAR:
				
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Kit Selector")) {
					
					new KitGUI(player);
					
				}
				
				break;
				
			case CHEST:
				
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Cosmetic Selector")) {
					
					e.setCancelled(true);
					
					player.updateInventory();
					
					// new CostmeticGUI
					
				}
				
				break;
				
			case ENDER_CHEST:
				
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Kit Shop")) {
					
					e.setCancelled(true);
					
					player.updateInventory();
					
					new ShopGUI(player);
					
				}
				
				break;
				
			case COMPASS:
				
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Warps")) {
					
					new WarpGUI(player);
					
				}
				
				break;
				
			case LEATHER:
				
				if (mPlayer.getInstance(player).getGroup().getRank().hasRequiredRank(Rank.MODPLUS)) {
					
					if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
					
					if (RegionManager.getInstance().getRegions(e.getClickedBlock().getLocation().toVector()).isEmpty()) {
						
						Chat.getInstance().messagePlayer(player, Chat.STAFF_NOTIFICATION + "No regions are defined here!");
						
						return;
					}
					
					StringBuilder sb = new StringBuilder();
					
					for (Region region : RegionManager.getInstance().getRegions(e.getClickedBlock().getLocation().toVector())) {
						
						sb.append(region.getName() + ", ");
						
					}
					
					String regionList = sb.toString();
					
					Pattern pattern = Pattern.compile(", $");
					Matcher matcher = pattern.matcher(regionList);
					
					regionList = matcher.replaceAll("");
					
					Chat.getInstance().messagePlayer(player, Chat.STAFF_NOTIFICATION + "Regions defined here: " + Chat.SECONDARY_BASE + regionList);
					
				}
				
				break;

			default:
				
				if (mKitUser.getInstance(player).hasPreviousKit() && !mKitUser.getInstance(player).hasKit()) {
					
					if (item.getType() == Material.WATCH) {
						
						mKitUser.getInstance(player).getPreviousKit().apply(player);
						
						player.updateInventory();
						
					}
					
				}
				
				break;
			}
			
		}
	}
}
