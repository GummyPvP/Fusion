package fusion.utils.editing;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class EditorSession {
	
	Vector p1;
	Vector p2;
	
	Editor editor;
	Player player;
	
	public EditorSession(Player player, Editor editor) {
		
		this.player = player;
		this.editor = editor;
		
	}
	
	public void setPosition1(Block block) {
		
		p1 = block.getLocation().toVector();
		
	}
	
	public void setPosition2(Block block) {
		
		p2 = block.getLocation().toVector();
		
	}
	
	public Vector getPosition1() {
		
		return p1;
		
	}
	
	public Vector getPosition2() {
		
		return p2;
		
	}
	
	public Editor getEditor() {
		
		return editor;
		
	}

	public Player getPlayer() {
		return player;
	}

}
