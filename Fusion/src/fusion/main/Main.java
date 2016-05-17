package fusion.main;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fusion.kits.Archer;
import fusion.kits.KitManager;
import fusion.kits.PVP;
import fusion.utils.command.CommandFramework;
import fusion.utils.spawn.Spawn;

/**
	 * 
	 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	private CommandFramework framework;
	
	public void onEnable() {
		
		instance = this;
		framework = new CommandFramework(this);
		
		log ("Instance & framework created");
		
		loadListeners();
		
		log ("Listeners loaded");
		
		framework.registerCommands(new PVP());
		framework.registerCommands(new Archer());
		
		log ("Commands loaded");
		
		KitManager.getInstance().registerKit(new PVP());
		KitManager.getInstance().registerKit(new Archer());
		
		log ("Kits loaded");
		
		Spawn.getInstance().load();
		
		log ("Spawn loaded");
		
	}
	
	public void onDisable() {
		
		KitManager.getInstance().unloadKits();
		
		log ("Kits unloaded");
		
		Spawn.getInstance().save();
		
	}
	
	private void loadListeners(Listener... listeners) {
		
		for (Listener l : listeners) {
			
			Bukkit.getPluginManager().registerEvents(l, this);
			
		}
		
	}
	
	public static Main getInstance() {
		
		return instance;
		
	}
	
	private void log(String s) {
		
		System.out.println(s);
		
	}

}
