package fusion.main;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fusion.cmds.Balance;
import fusion.cmds.ClearKit;
import fusion.cmds.CombatLogCommand;
import fusion.cmds.KitCommand;
import fusion.cmds.SetSpawn;
import fusion.cmds.SpawnCommand;
import fusion.cmds.Test;
import fusion.kits.Archer;
import fusion.kits.Fisherman;
import fusion.kits.Heavy;
import fusion.kits.PVP;
import fusion.kits.Stomper;
import fusion.kits.Thor;
import fusion.kits.Viper;
import fusion.kits.listeners.FishEvent;
import fusion.kits.listeners.StomperEvent;
import fusion.kits.listeners.ThorEvent;
import fusion.kits.listeners.ViperEvent;
import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
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
import fusion.listeners.TabComplete;
import fusion.utils.mKitUser;
import fusion.utils.command.CommandFramework;
import fusion.utils.editing.EditorManager;
import fusion.utils.editing.ToolClick;
import fusion.utils.editing.cmds.RegionCreate;
import fusion.utils.editing.cmds.RegionDelete;
import fusion.utils.editing.cmds.RegionList;
import fusion.utils.editing.cmds.SetFlag;
import fusion.utils.editing.editors.RegionEditor;
import fusion.utils.protection.BlockBreak;
import fusion.utils.protection.BlockBurn;
import fusion.utils.protection.BlockDecay;
import fusion.utils.protection.BlockIgnite;
import fusion.utils.protection.BlockPlace;
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
				new ToolClick(), new RegionEditor(), new PlayerDamage(), new DropItem(), new MobSpawn(), new BlockIgnite(),
				new BlockDecay(), new BlockBurn(), new ThorEvent(), new TabComplete());
		
		log ("Listeners loaded");
		
		loadCommands(new KitCommand(), new Test(), new WarpCreate(), new WarpList(), new SetSpawn(), new SpawnCommand(), new RegionCreate(), new RegionList(), 
				new SetFlag(), new WarpDelete(), new RegionDelete(), new Balance(), new CombatLogCommand(), new ClearKit());
		
		log ("Commands loaded");
		
		loadKits(new PVP(), new Archer(), new Fisherman(), new Stomper(), new Viper(), new Heavy(), new Thor());
		
		log ("Kits loaded");
		
		Spawn.getInstance().load();
		
		log ("Spawn loaded");
		
		WarpManager.getInstance().loadWarps();
		
		log ("Warps loaded");
		
		EditorManager.getInstance().loadEditors();
		
		RegionManager.getInstance().loadRegions();
		
		if (!Bukkit.getOnlinePlayers().isEmpty()) {
			
			for (Player online : Bukkit.getOnlinePlayers()) {
				
				mKitUser.getInstance(online).load();
				
			}
			
		}
		
		long finishTime = System.currentTimeMillis();
		
		log ("Finished in: " + TimeUnit.MICROSECONDS.toSeconds(finishTime - startTime) + " seconds"); 
		
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
