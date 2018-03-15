package fusion.utils.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.ItemBuilder;
import fusion.utils.Settings;
import fusion.utils.warps.Warp;
import fusion.utils.warps.WarpManager;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class WarpGUI extends GUI {
	
	public static String INVENTORY_NAME = ChatColor.translateAlternateColorCodes('&', Settings.getSettings().WARP_GUI_NAME);
	
	public WarpGUI(Player player) {
		
		super(27, INVENTORY_NAME, player);
		
		player.openInventory(getInventory());
		
	}

	@Override
	public void populateInventory() {
		
		ItemStack glass = new ItemBuilder(Material.THIN_GLASS).name(" ").build();

		for (int i = 0; i < 9; i++) {

			inv.setItem(i, glass);

		}

		for (int i = (inv.getSize() - 9); i < inv.getSize(); i++) {

			inv.setItem(i, glass);
			
		}
		
		for (Warp warps : WarpManager.getInstance().getWarps()) {
			
			getInventory().addItem(warps.getInventoryItem());
			
		}
		
	}
}
