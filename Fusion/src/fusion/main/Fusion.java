package fusion.main;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import fusion.cmds.Balance;
import fusion.cmds.CandyManCommands;
import fusion.cmds.ClearKit;
import fusion.cmds.CombatLogCommand;
import fusion.cmds.EcoGive;
import fusion.cmds.EcoSet;
import fusion.cmds.FreeKitFriday;
import fusion.cmds.KitCommand;
import fusion.cmds.Pay;
import fusion.cmds.SetGladiator;
import fusion.cmds.SetSpawn;
import fusion.cmds.SpawnCommand;
import fusion.cmds.Stats;
import fusion.cmds.Test;
import fusion.events.ArenaManager;
import fusion.events.EventManager;
import fusion.events.cmds.EventCommand;
import fusion.events.cmds.EventJoin;
import fusion.events.listeners.EventInventoryClick;
import fusion.kits.Archer;
import fusion.kits.Endermage;
import fusion.kits.Fisherman;
import fusion.kits.Gladiator;
import fusion.kits.Heavy;
import fusion.kits.Ninja;
import fusion.kits.PVP;
import fusion.kits.Sanic;
import fusion.kits.Santa;
import fusion.kits.Shark;
import fusion.kits.Snail;
import fusion.kits.SpellCaster;
import fusion.kits.Stomper;
import fusion.kits.Switcher;
import fusion.kits.Thor;
import fusion.kits.Turtle;
import fusion.kits.Vampire;
import fusion.kits.Vigilante;
import fusion.kits.Viper;
import fusion.kits.Wimp;
import fusion.kits.listeners.EndermageEvent;
import fusion.kits.listeners.FishEvent;
import fusion.kits.listeners.GladiatorEvent;
import fusion.kits.listeners.NinjaEvent;
import fusion.kits.listeners.SharkEvent;
import fusion.kits.listeners.SnailEvent;
import fusion.kits.listeners.SpellCasterEvent;
import fusion.kits.listeners.StomperEvent;
import fusion.kits.listeners.SwitchEvent;
import fusion.kits.listeners.ThorEvent;
import fusion.kits.listeners.TurtleEvent;
import fusion.kits.listeners.VampireEvent;
import fusion.kits.listeners.VigilanteEvent;
import fusion.kits.listeners.ViperEvent;
import fusion.kits.listeners.WimpEvent;
import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.listeners.AsyncPlayerChat;
import fusion.listeners.ChunkLoad;
import fusion.listeners.ChunkUnload;
import fusion.listeners.CombatLog;
import fusion.listeners.CommandPreprocess;
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
import fusion.listeners.PlayerMove;
import fusion.listeners.PlayerQuit;
import fusion.listeners.PlayerRespawn;
import fusion.listeners.PlayerTeleport;
import fusion.listeners.PlayerUpdateRankEvent;
import fusion.listeners.TabComplete;
import fusion.teams.cmds.TeamCommand;
import fusion.teams.utils.TeamManager;
import fusion.utils.ConfigManager;
import fusion.utils.StatsManager;
import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.command.CommandFramework;
import fusion.utils.crates.CrateManager;
import fusion.utils.editing.EditorManager;
import fusion.utils.editing.TeleportListener;
import fusion.utils.editing.ToolClick;
import fusion.utils.editing.cmds.RegionCreate;
import fusion.utils.editing.cmds.RegionDelete;
import fusion.utils.editing.cmds.RegionList;
import fusion.utils.editing.cmds.SetFlag;
import fusion.utils.editing.editors.RegionEditor;
import fusion.utils.gui.EventJoinGUI;
import fusion.utils.protection.BlockBreak;
import fusion.utils.protection.BlockBurn;
import fusion.utils.protection.BlockDecay;
import fusion.utils.protection.BlockIgnite;
import fusion.utils.protection.BlockPlace;
import fusion.utils.protection.Bounds;
import fusion.utils.protection.PlayerDamage;
import fusion.utils.protection.RegionManager;
import fusion.utils.spawn.Spawn;
import fusion.utils.warps.WarpCreate;
import fusion.utils.warps.WarpDelete;
import fusion.utils.warps.WarpList;
import fusion.utils.warps.WarpManager;

/**
 * 
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Fusion extends JavaPlugin {

	private static Fusion instance;

	private CommandFramework framework;
	public boolean freekitfriday = false;
	private ConfigManager spawn, warps, regions, config, kitInfo, teams, arena;

	private int day;
	
	public void onEnable() {

		long startTime = System.nanoTime();

		instance = this;
		framework = new CommandFramework(this);

		spawn = new ConfigManager("spawn", false);
		warps = new ConfigManager("warps", false);
		regions = new ConfigManager("regions", false);
		config = new ConfigManager("config", false);
		kitInfo = new ConfigManager("kit_info", false);
		teams = new ConfigManager("teams", false);
		arena = new ConfigManager("arena", false);
		
		// registerEntity(CandyMan.class, "Candyman", 120);

		log("Instance & framework created");

		loadListeners(new AsyncPlayerChat(), new InventoryClick(), new PlayerTeleport(), new TeleportListener(), new PlayerInteract(),
				new FoodChange(), new FishEvent(), new StomperEvent(), new ViperEvent(), new PlayerDeath(),
				new PlayerJoin(), new PlayerQuit(), new PlayerRespawn(), new EntityDamageByEntity(), new ItemPickup(),
				new BlockPlace(), new BlockBreak(), new ToolClick(), new RegionEditor(), new PlayerDamage(),
				new DropItem(), new MobSpawn(), new BlockIgnite(), new BlockDecay(), new BlockBurn(), new ThorEvent(),
				new TabComplete(), new ChunkUnload(), new ChunkLoad(), new PlayerInteractEntity(), new SwitchEvent(),
				new EndermageEvent(), new CommandPreprocess(), new SnailEvent(), new NinjaEvent(), new SharkEvent(),
				new GladiatorEvent(), new PlayerUpdateRankEvent(), new PlayerMove(), new WimpEvent(),
				new SpellCasterEvent(), new TurtleEvent(), new VampireEvent(), new VigilanteEvent(), EventManager.get(), new EventInventoryClick());

		log("Listeners loaded");

		loadCommands(new KitCommand(), new Test(), new WarpCreate(), new WarpList(), new SetSpawn(), new SpawnCommand(),
				new RegionCreate(), new RegionList(), new SetFlag(), new WarpDelete(), new RegionDelete(),
				new Balance(), new CombatLogCommand(), new ClearKit(), new CandyManCommands(), new EcoSet(),
				new SetGladiator(), new EcoGive(), new FreeKitFriday(), new Pay(), new TeamCommand(), new Stats(), new EventCommand(), new EventJoin());

		log("Commands loaded");

		loadKits(new PVP(), new Archer(), new Fisherman(), new Stomper(), new Viper(), new Heavy(), new Thor(),
				new Switcher(), new Endermage(), new Sanic(), new Shark(), new Ninja(), new Snail(), new Gladiator(),
				new Wimp(), new SpellCaster(), new Vampire(), new Turtle(), new Vigilante(), new Santa());

		log("Kits loaded");

		Spawn.getInstance().load();

		log("Spawn loaded");

		WarpManager.getInstance().loadWarps();

		log("Warps loaded");

		TeamManager.get().loadTeams();

		log("Teams loaded");
		
		registerPlaceHolders();

		StatsManager.getInstance().setup(this);

		EditorManager.getInstance().loadEditors();

		RegionManager.getInstance().loadRegions();

		CrateManager.getInstance().loadCrates();

		StatsManager.getInstance().startScoreboard(this);
		
		ConfigurationSerialization.registerClass(Bounds.class);
		
		ArenaManager.get().loadArenas();
		
		EventManager.get().start();
		
		EventJoinGUI.get().populateInventory();
		
		if (Bukkit.getOnlinePlayers().size() != 0) {

			for (Player online : Bukkit.getOnlinePlayers()) {

				mKitUser.getInstance(online).load();

			}

		}
		
		Utils.get().load();
		
		freeKitFriday();

		long finishTime = System.nanoTime();

		log("Finished in: " + TimeUnit.NANOSECONDS.toMillis(finishTime - startTime) + " ms");

	}

	public void onDisable() {

		TeamManager.get().saveTeams();

		log("Teams saved");

		KitManager.getInstance().unloadKits();

		Spawn.getInstance().save();
		
		ArenaManager.get().saveArenas();

		for (Player online : Bukkit.getOnlinePlayers()) {

			mKitUser.getInstance(online).save();

			if (CombatLog.getInstance().isInCombat(online)) {
				CombatLog.getInstance().remove(online);
			}

		}

		WarpManager.getInstance().saveWarps();

		RegionManager.getInstance().saveRegions();

	}

	public void registerPlaceHolders() {

		if (!Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			
			System.out.println("PlaceholderAPI is not enabled, placeholders will not be registered!");
			
			return;
		}
		
		// kills
		PlaceholderAPI.registerPlaceholder(this, "fusion_kills", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

				if (event.getPlayer() == null) {
					return "0";
				}

				Player player = event.getPlayer();
				
				mKitUser user = mKitUser.getInstance(player);
				
				return String.valueOf(user.getKills());

			}
		});

		// deaths
		PlaceholderAPI.registerPlaceholder(this, "fusion_deaths", new PlaceholderReplacer() {
			
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

				if (event.getPlayer() == null) {
					return "0";
				}

				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);

				return String.valueOf(user.getDeaths());

			}
		});

		// candies
		PlaceholderAPI.registerPlaceholder(this, "fusion_candies", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

				if (event.getPlayer() == null) {
					return "0";
				}

				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);

				return String.valueOf(user.getCandies());

			}
		});

		// currentkit
		PlaceholderAPI.registerPlaceholder(this, "fusion_currentkit", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

				if (event.getPlayer() == null) {
					return "none";
				}

				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);

				if (user.getKit() == null) {
					return "none";
				}

				return String.valueOf(user.getKit().getName());

			}
		});

		// current team
		PlaceholderAPI.registerPlaceholder(this, "fusion_team", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

				if (event.getPlayer() == null) {
					return "none";
				}

				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);

				if (user.getTeam() == null) {
					return "none";
				}

				return String.valueOf(user.getTeam().getName());

			}
		});

		// current teammembers online
		PlaceholderAPI.registerPlaceholder(this, "fusion_team_online", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

				if (event.getPlayer() == null) {
					return ChatColor.GREEN + "Online Team Members: &fnone";
				}

				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);

				if (user.getTeam() == null) {
					return ChatColor.GREEN + "Online Team Members: &fYou are not in a team!";
				}

				StringBuilder sb = new StringBuilder();

				sb.append(ChatColor.GREEN + "Online Team Members: ");

				int ontm = 0;

				for (UUID on : user.getTeam().getMembers().keySet()) {

					Player tplayer = Bukkit.getPlayer(on);

					if (tplayer != event.getPlayer()) {

						if (tplayer != null) {

							sb.append(ChatColor.WHITE + tplayer.getName() + ", ");

							ontm++;

						}
					}

				}
				
				if (ontm == 0) {
					sb.append(ChatColor.WHITE + "none");
				}
				
				return Utils.removeLast(sb);

			}
		});

		// combattag
		PlaceholderAPI.registerPlaceholder(this, "fusion_ct", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
				
				if (event.getPlayer() == null) {
					return ChatColor.GREEN + "Not in combat";
				}
				
				Player player = event.getPlayer();

				if (!CombatLog.getInstance().isInCombat(player)) {
					return ChatColor.GREEN + "Not in combat";
				}

				return ChatColor.RED + String.valueOf(CombatLog.getInstance().getRemainingTime(player));

			}
		});
		
		// kd
		PlaceholderAPI.registerPlaceholder(this, "fkd", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
				
				if (event.getPlayer() == null) {
					return ChatColor.WHITE + "0";
				}
				
				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);
				
				DecimalFormat dm = new DecimalFormat("#.##");
				double kd = 0.0;
				
				if (user.getKills() != 0) {

					kd = (double) user.getKills() / (double) user.getDeaths();
					
				}
				
				String kdr = dm.format(kd);
				
				return kdr;

			}
		});
		
		// killstreak
		PlaceholderAPI.registerPlaceholder(this, "fusion_killstreak", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
				
				if (event.getPlayer() == null) {
					return ChatColor.WHITE + "0";
				}
				
				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);
				
				return String.valueOf(user.getKillStreak());

			}
		});

		log("Place Holders have been registered!");

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

	public ConfigManager getKitInfoFile() {
		return kitInfo;
	}

	public ConfigManager getTeamsFile() {
		return teams;
	}

	public ConfigManager getArenaFile() {
		return arena;
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

		System.out.println("[Fusion] " + s);

	}
	public void freeKitFriday() {
		
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				
				day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
				
				if (day == 6 && !freekitfriday) {
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "freekitfriday");
					
				}
				if (day != 6 && freekitfriday) {
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "freekitfriday");
					
				}
				
			}
		}, 20L, 20 * 5L);
		
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

	// @SuppressWarnings("unchecked")
	// public static void registerEntity(Class<? extends EntityInsentient>
	// clazz, String name, int id) {
	// try {
	// Field field_c = EntityTypes.class.getDeclaredField("c");
	// Field field_d = EntityTypes.class.getDeclaredField("d");
	// Field field_f = EntityTypes.class.getDeclaredField("f");
	// Field field_g = EntityTypes.class.getDeclaredField("g");
	// field_c.setAccessible(true);
	// field_d.setAccessible(true);
	// field_f.setAccessible(true);
	// field_g.setAccessible(true);
	//
	// Map<String, Class<?>> c = (Map<String, Class<?>>) field_c.get(field_c);
	// Map<Class<?>, String> d = (Map<Class<?>, String>) field_d.get(field_d);
	// Map<Class<?>, Integer> f = (Map<Class<?>, Integer>) field_f.get(field_f);
	// Map<String, Integer> g = (Map<String, Integer>) field_g.get(field_g);
	//
	// Iterator<String> i = c.keySet().iterator();
	// while (i.hasNext()) {
	// String s = i.next();
	// if (s.equals(name)) {
	// i.remove();
	// }
	// }
	//
	// Iterator<Class<?>> i2 = d.keySet().iterator();
	// while (i2.hasNext()) {
	// Class<?> cl = i2.next();
	// if (cl.getCanonicalName().equals(clazz.getCanonicalName())) {
	// i2.remove();
	// }
	// }
	//
	// Iterator<Class<?>> i3 = f.keySet().iterator();
	// while (i2.hasNext()) {
	// Class<?> cl = i3.next();
	// if (cl.getCanonicalName().equals(clazz.getCanonicalName())) {
	// i3.remove();
	// }
	// }
	//
	// Iterator<String> i4 = g.keySet().iterator();
	// while (i4.hasNext()) {
	// String s = i4.next();
	// if (s.equals(name)) {
	// i4.remove();
	// }
	// }
	//
	// c.put(name, clazz);
	// d.put(clazz, name);
	// f.put(clazz, id);
	// g.put(name, id);
	// } catch (Exception e) {
	//
	// System.out.println("Couldn't register " + name + " ID: " + id + "!");
	//
	// e.printStackTrace();
	// }
	// }

}
