package fusion.utils.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fusion.utils.ItemBuilder;

/**
 * 
 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class TestGUI extends GUI {

	public TestGUI(Player player) {

		super(9, "Test GUI", player);

		player.openInventory(getInventory());

	}

	@Override
	public void populateInventory() {

		Inventory inv = getInventory();

		inv.addItem(new ItemBuilder(Material.DIAMOND).name("Test Diamond!").build());

	}
}
