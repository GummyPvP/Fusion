package fusion.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fusion.main.Fusion;

/**
 * 
 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class TestGUI extends MenuGUI {
	
	public TestGUI(Player player) {
		
		super (player, "TestGUI", 9, Fusion.getInstance(), false);
		
	}
	
	@Override
	public void onClick(InventoryClickEvent e) {
		
		
		
	}
	
}
