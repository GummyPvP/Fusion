package fusion.main;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fusion.cmds.Balance;
import fusion.cmds.CandyManCommands;
import fusion.cmds.ClearKit;
import fusion.cmds.CombatLogCommand;
import fusion.cmds.KitCommand;
import fusion.cmds.SetSpawn;
import fusion.cmds.SpawnCommand;
import fusion.cmds.Test;
import fusion.events.utils.EventManager;
import fusion.kits.Archer;
import fusion.kits.Endermage;
import fusion.kits.Fisherman;
import fusion.kits.Heavy;
import fusion.kits.PVP;
import fusion.kits.Stomper;
import fusion.kits.Switcher;
import fusion.kits.Thor;
import fusion.kits.Viper;
import fusion.kits.listeners.EndermageEvent;
import fusion.kits.listeners.FishEvent;
import fusion.kits.listeners.StomperEvent;
import fusion.kits.listeners.SwitchEvent;
import fusion.kits.listeners.ThorEvent;
import fusion.kits.listeners.ViperEvent;
import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.listeners.ChunkLoad;
import fusion.listeners.ChunkUnload;
import fusion.listeners.DropItem;
import fusion.listeners.EntityDamageByEntity;
import fusion.listeners.FoodChange;
import fusion.listeners.InventoryClick;
import fusion.listeners.ItemPickup;
import fusion.listeners.MobSpawn;
import fusion.listeners.PlayerDeath;
import fusion.listeners.PlayerInteract;
import fusion.listeners.PlayerInteractEntity;
import fusion.listeners.PlayerJoin;
import fusion.listeners.PlayerQuit;
import fusion.listeners.PlayerRespawn;
import fusion.listeners.TabComplete;
import fusion.utils.CandyMan;
import fusion.utils.ConfigManager;
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
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityTypes;

/**
 * 
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Fusion extends JavaPlugin {

	private static Fusion instance;

	private CommandFramework framework;
	
	private ConfigManager spawn, warps, regions, config;

	public void onEnable() {

		long startTime = System.currentTimeMillis();

		instance = this;
		framework = new CommandFramework(this);
		
		spawn = new ConfigManager("spawn", false);
		warps = new ConfigManager("warps", false);
		regions = new ConfigManager("regions", false);
		config = new ConfigManager("config", false);
		
		registerEntity(CandyMan.class, "Candyman", 120);

		log("Instance & framework created");

		loadListeners(new InventoryClick(), new PlayerInteract(), new FoodChange(), new FishEvent(), new StomperEvent(),
				new ViperEvent(), new PlayerDeath(), new PlayerJoin(), new PlayerQuit(), new PlayerRespawn(),
				new EntityDamageByEntity(), new ItemPickup(), new BlockPlace(), new BlockBreak(), new ToolClick(),
				new RegionEditor(), new PlayerDamage(), new DropItem(), new MobSpawn(), new BlockIgnite(),
				new BlockDecay(), new BlockBurn(), new ThorEvent(), new TabComplete(), new ChunkUnload(),
				new ChunkLoad(), new PlayerInteractEntity(), new SwitchEvent(), new EndermageEvent());

		log("Listeners loaded");

		loadCommands(new KitCommand(), new Test(), new WarpCreate(), new WarpList(), new SetSpawn(), new SpawnCommand(),
				new RegionCreate(), new RegionList(), new SetFlag(), new WarpDelete(), new RegionDelete(),
				new Balance(), new CombatLogCommand(), new ClearKit(), new CandyManCommands());

		log("Commands loaded");

		loadKits(new PVP(), new Archer(), new Fisherman(), new Stomper(), new Viper(), new Heavy(), new Thor(),
				new Switcher(), new Endermage());

		log("Kits loaded");

		Spawn.getInstance().load();

		log("Spawn loaded");

		WarpManager.getInstance().loadWarps();

		log("Warps loaded");

		EventManager.getInstance().registerEvents();

		EditorManager.getInstance().loadEditors();

		RegionManager.getInstance().loadRegions();

		if (Bukkit.getOnlinePlayers().length != 0) {

			for (Player online : Bukkit.getOnlinePlayers()) {

				mKitUser.getInstance(online).load();

			}

		}

		long finishTime = System.currentTimeMillis();

		log("Finished in: " + TimeUnit.MILLISECONDS.toSeconds(finishTime - startTime) + " seconds");

	}
	
	public void onDisable() {

		KitManager.getInstance().unloadKits();

		log("Kits unloaded");

		Spawn.getInstance().save();

		for (Player online : Bukkit.getOnlinePlayers()) {

			mKitUser.getInstance(online).save();

		}

		WarpManager.getInstance().saveWarps();

		RegionManager.getInstance().saveRegions();

	}
	
	public ConfigManager getConfiguration() {
		return config;
	}
	
	public ConfigManager getSpawnFile() {
		return spawn;
	}
	
	public ConfigManager getRegionsFile() {
		return regions;
	}
	
	public ConfigManager getWarpsFile() {
		return warps;
	}
	
	public ConfigManager getPlayerFile(String player) {
		return ConfigManager.getPlayerFile(player);
	}
	
	private void loadListeners(Listener... listeners) {

		for (Listener l : listeners) {

			Bukkit.getPluginManager().registerEvents(l, this);

		}

	}

	private void loadCommands(Object... objects) {

		for (Object object : objects) {

			framework.registerCommands(object);

		}

	}

	private void loadKits(Kit... kits) {

		for (Kit kit : kits) {

			KitManager.getInstance().registerKit(kit);

		}

	}

	public static Fusion getInstance() {

		return instance;

	}

	private void log(String s) {

		System.out.println(s);

	}

	/**
	 * 
	 * Copyright EchoPet - Not our code!!!
	 * 
	 * @param clazz
	 *            - Class that holds our custom entity
	 * @param name
	 *            - User friendly name? I don't exactly know what it's for
	 * @param id
	 *            - The network ID for the client to use
	 */

	@SuppressWarnings("unchecked")
	public static void registerEntity(Class<? extends EntityInsentient> clazz, String name, int id) {
		try {
			Field field_c = EntityTypes.class.getDeclaredField("c");
			Field field_d = EntityTypes.class.getDeclaredField("d");
			Field field_f = EntityTypes.class.getDeclaredField("f");
			Field field_g = EntityTypes.class.getDeclaredField("g");
			field_c.setAccessible(true);
			field_d.setAccessible(true);
			field_f.setAccessible(true);
			field_g.setAccessible(true);

			Map<String, Class<?>> c = (Map<String, Class<?>>) field_c.get(field_c);
			Map<Class<?>, String> d = (Map<Class<?>, String>) field_d.get(field_d);
			Map<Class<?>, Integer> f = (Map<Class<?>, Integer>) field_f.get(field_f);
			Map<String, Integer> g = (Map<String, Integer>) field_g.get(field_g);

			Iterator<String> i = c.keySet().iterator();
			while (i.hasNext()) {
				String s = i.next();
				if (s.equals(name)) {
					i.remove();
				}
			}

			Iterator<Class<?>> i2 = d.keySet().iterator();
			while (i2.hasNext()) {
				Class<?> cl = i2.next();
				if (cl.getCanonicalName().equals(clazz.getCanonicalName())) {
					i2.remove();
				}
			}

			Iterator<Class<?>> i3 = f.keySet().iterator();
			while (i2.hasNext()) {
				Class<?> cl = i3.next();
				if (cl.getCanonicalName().equals(clazz.getCanonicalName())) {
					i3.remove();
				}
			}

			Iterator<String> i4 = g.keySet().iterator();
			while (i4.hasNext()) {
				String s = i4.next();
				if (s.equals(name)) {
					i4.remove();
				}
			}

			c.put(name, clazz);
			d.put(clazz, name);
			f.put(clazz, id);
			g.put(name, id);
		} catch (Exception e) {

			System.out.println("Couldn't register " + name + " ID: " + id + "!");

			e.printStackTrace();
		}
	}

}
