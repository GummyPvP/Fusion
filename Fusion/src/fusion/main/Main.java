package fusion.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fusion.cmds.KitCommand;
import fusion.cmds.Test;
import fusion.kits.Archer;
import fusion.kits.Fisherman;
import fusion.kits.Heavy;
import fusion.kits.PVP;
import fusion.kits.Stomper;
import fusion.kits.Viper;
import fusion.kits.listeners.FishEvent;
import fusion.kits.listeners.StomperEvent;
import fusion.kits.listeners.ViperEvent;
import fusion.kits.utils.KitManager;
import fusion.listeners.FoodChange;
import fusion.listeners.InventoryClick;
import fusion.listeners.PlayerDeath;
import fusion.listeners.PlayerInteract;
import fusion.listeners.PlayerJoin;
import fusion.utils.mKitUser;
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
		
		loadListeners(new InventoryClick(), new PlayerInteract(), new FoodChange(), new FishEvent(), new StomperEvent(), new ViperEvent(), new PlayerDeath(), new PlayerJoin());
		
		log ("Listeners loaded");
		
		framework.registerCommands(new KitCommand());
		framework.registerCommands(new Test());
		framework.registerCommands(new PVP());
		framework.registerCommands(new Archer());
		framework.registerCommands(new Fisherman());
		framework.registerCommands(new Stomper());
		framework.registerCommands(new Viper());
		framework.registerCommands(new Heavy());
		
		log ("Commands loaded");
		
		KitManager.getInstance().registerKit(new PVP());
		KitManager.getInstance().registerKit(new Archer());
		KitManager.getInstance().registerKit(new Fisherman());
		KitManager.getInstance().registerKit(new Stomper());
		KitManager.getInstance().registerKit(new Viper());
		KitManager.getInstance().registerKit(new Heavy());
		
		log ("Kits loaded");
		
		Spawn.getInstance().load();
		
		log ("Spawn loaded");
		
	}
	
	public void onDisable() {
		
		KitManager.getInstance().unloadKits();
		
		log ("Kits unloaded");
		
		Spawn.getInstance().save();
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			
			mKitUser.getInstance(online).save();
			
		}
		
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
