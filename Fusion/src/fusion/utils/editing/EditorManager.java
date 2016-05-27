package fusion.utils.editing;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;

/**
	 * 
	 * Copyright GummyPvP. Created on May 26, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class EditorManager {
	
	private EditorManager() { }
	
	private static EditorManager instance = new EditorManager();
	
	public static EditorManager getInstance() {
		
		return instance;
		
	}
	
	private Set<Editor> editors = new HashSet<Editor>();
	
	public void registerEditor(Editor editor) {
		
		editors.add(editor);
		
	}
	
	public Editor getEditor(String name) {
		
		for (Editor editorz : editors) {
			
			if (editorz.getName().equalsIgnoreCase(name)) return editorz;
			
		}
		
		return null;
		
	}
	
	public Editor getEditor(Material tool) {
		
		for (Editor editorz : editors) {
			
			if (editorz.getTool() == tool) return editorz;
			
		}
		
		return null;
		
	}

}
