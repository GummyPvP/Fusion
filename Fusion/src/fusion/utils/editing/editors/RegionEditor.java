package fusion.utils.editing.editors;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import fusion.utils.Chat;
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
		
		Action action = e.getAction();
		
		if (action.toString().contains("RIGHT")) {
			
			e.getSession().setPosition2(e.getPoint());
			
			Chat.getInstance().messagePlayer(e.getPlayer(), Chat.STAFF_NOTIFICATION + "Set Pos #2");
			
			return;
		}
		
		e.getSession().setPosition1(e.getPoint());
		
		Chat.getInstance().messagePlayer(e.getPlayer(), Chat.STAFF_NOTIFICATION + "Set Pos #1");
		
	}

}
