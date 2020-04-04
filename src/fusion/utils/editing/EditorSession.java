package fusion.utils.editing;

import org.bukkit.entity.Player;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class EditorSession {
	
	Editor editor;
	Player player;
	
	public EditorSession(Player player, Editor editor) {
		
		this.player = player;
		this.editor = editor;
		
	}
	
	public Editor getEditor() {
		
		return editor;
		
	}

	public Player getPlayer() {
		return player;
	}

}
