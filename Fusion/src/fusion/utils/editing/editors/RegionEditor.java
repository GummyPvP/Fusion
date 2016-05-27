package fusion.utils.editing.editors;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fusion.utils.editing.Editor;
import fusion.utils.editing.event.PlayerSelectPointEvent;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class RegionEditor extends Editor implements Listener {

	@Override
	public String getName() {
		return "RegionEditor";
	}

	@Override
	public Material getTool() {
		return Material.GOLD_HOE;
	}
	
	@EventHandler
	public void onSelect(PlayerSelectPointEvent e) {
		
		//Action action = e.getAction();
		
	}

}
