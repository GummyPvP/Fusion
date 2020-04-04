package fusion.utils.editing.editors;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import fusion.utils.chat.Chat;
import fusion.utils.editing.Editor;
import fusion.utils.editing.event.PlayerSelectPointEvent;

/**
 * 
 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class RegionEditor extends Editor implements Listener {

	private Vector p1, p2;

	@Override
	public String getName() {
		return "RegionEditor";
	}

	@Override
	public Material getTool() {
		return Material.GOLDEN_HOE;
	}

	@Override
	public RegionEditor clone() {

		return new RegionEditor();

	}

	public void setPosition1(Vector block) {

		p1 = block;

	}

	public void setPosition2(Vector block) {

		p2 = block;

	}

	public Vector getPosition1() {

		return p1;

	}

	public Vector getPosition2() {

		return p2;

	}

	@EventHandler
	public void onSelect(PlayerSelectPointEvent e) {

		Action action = e.getAction();

		if (e.getSession().getEditor() instanceof RegionEditor) {

			RegionEditor editor = (RegionEditor) e.getSession().getEditor();

			if (action.toString().contains("RIGHT")) {

				editor.setPosition2(e.getPoint());

				Chat.getInstance().messagePlayer(e.getSession().getPlayer(),
						Chat.STAFF_NOTIFICATION + "Set position #2");

				return;
			}

			editor.setPosition1(e.getPoint());

			Chat.getInstance().messagePlayer(e.getSession().getPlayer(), Chat.STAFF_NOTIFICATION + "Set position #1");
		}

	}

}
