package fusion.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fusion.cmds.KitCommand;
import fusion.cmds.SetSpawn;
import fusion.cmds.SpawnCommand;
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
import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.listeners.BlockBreak;
import fusion.listeners.BlockPlace;
import fusion.listeners.DropItem;
import fusion.listeners.EntityDamageByEntity;
import fusion.listeners.FoodChange;
import fusion.listeners.InventoryClick;
import fusion.listeners.ItemPickup;
import fusion.listeners.MobSpawn;
import fusion.listeners.PlayerDeath;
import fusion.listeners.PlayerInteract;
import fusion.listeners.PlayerJoin;
import fusion.listeners.PlayerQuit;
import fusion.listeners.PlayerRespawn;
import fusion.utils.mKitUser;
import fusion.utils.command.CommandFramework;
import fusion.utils.editing.EditorManager;
import fusion.utils.editing.ToolClick;
import fusion.utils.editing.cmds.AddFlag;
import fusion.utils.editing.cmds.RegionCreate;
import fusion.utils.editing.cmds.RegionList;
import fusion.utils.editing.editors.RegionEditor;
import fusion.utils.protection.PlayerDamage;
import fusion.utils.protection.RegionManager;
import fusion.utils.spawn.Spawn;
import fusion.utils.warps.WarpCreate;
import fusion.utils.warps.WarpDelete;
import fusion.utils.warps.WarpList;
import fusion.utils.warps.WarpManager;

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
		
		long startTime = System.currentTimeMillis();
		
		instance = this;
		framework = new CommandFramework(this);
		
		log ("Instance & framework created");
		
		loadListeners(new InventoryClick(), new PlayerInteract(), new FoodChange(), new FishEvent(), new StomperEvent(), new ViperEvent(), new PlayerDeath(), new PlayerJoin(),
				new PlayerQuit(), new PlayerRespawn(), new EntityDamageByEntity(), new ItemPickup(), new BlockPlace(), new BlockBreak(), 
				new ToolClick(), new RegionEditor(), new PlayerDamage(), new DropItem(), new MobSpawn());
		
		log ("Listeners loaded");
		
		loadCommands(new KitCommand(), new Test(), new PVP(), new Archer(), new Fisherman(), 
				new Stomper(), new Viper(), new Heavy(), new WarpCreate(), new WarpList(), new SetSpawn(), new SpawnCommand(), new RegionCreate(), new RegionList(), new AddFlag(), new WarpDelete());
		
		log ("Commands loaded");
		
		loadKits(new PVP(), new Archer(), new Fisherman(), new Stomper(), new Viper(), new Heavy());
		
		log ("Kits loaded");
		
		Spawn.getInstance().load();
		
		log ("Spawn loaded");
		
		WarpManager.getInstance().loadWarps();
		
		log ("Warps loaded");
		
		EditorManager.getInstance().loadEditors();
		
		RegionManager.getInstance().loadRegions();
		
		long finishTime = System.currentTimeMillis();
		
		log ("Finished in: " + (finishTime - startTime) / 1000 + " seconds"); 
		
	}
	
	public void onDisable() {
		
		KitManager.getInstance().unloadKits();
		
		log ("Kits unloaded");
		
		Spawn.getInstance().save();
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			
			mKitUser.getInstance(online).save();
			
		}
		
		WarpManager.getInstance().saveWarps();
		
		RegionManager.getInstance().saveRegions();
		
	}
	
	private void loadListeners(Listener... listeners) {
		
		for (Listener l : listeners) {
			
			Bukkit.getPluginManager().registerEvents(l, this);
			
		}
		
	}
	
	private void loadCommands(Object...objects) {
		
		for (Object object : objects) {
			
			framework.registerCommands(object);
			
		}
		
	}
	
	private void loadKits(Kit... kits) {
		
		for (Kit kit : kits) {
			
			KitManager.getInstance().registerKit(kit);
			
		}
		
	}
	
	public static Main getInstance() {
		
		return instance;
		
	}
	
	private void log(String s) {
		
		System.out.println(s);
		
	}

}
