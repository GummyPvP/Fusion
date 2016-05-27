package fusion.utils.editing;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class EditorSessions {
	
	private EditorSessions() { }
	
	private static EditorSessions instance = new EditorSessions();
	
	public static EditorSessions getInstance() {
		
		return instance;
		
	}
	
	private Set<EditorSession> sessions = new HashSet<EditorSession>();
	
	public void addSession(Player player, Editor editor) {
		
		sessions.add(new EditorSession(player, editor));
		
	}
	
	public EditorSession getSession(Player player) {
		
		for (EditorSession session : sessions) {
			
			if (session.getPlayer().getName().equalsIgnoreCase(player.getName())) return session;
			
		}
		
		return null;
		
	}

}
