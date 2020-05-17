package fusion.main;

import java.io.IOException;
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
import fusion.cmds.EventModeCommands;
import fusion.cmds.Help;
import fusion.cmds.KitCommand;
import fusion.cmds.Pay;
import fusion.cmds.SetSpawn;
import fusion.cmds.SetVigilante;
import fusion.cmds.SpawnCommand;
import fusion.cmds.Stats;
import fusion.event.cmds.EventCommands;
import fusion.event.util.EventHandler;
import fusion.kits.Anchor;
import fusion.kits.Archer;
import fusion.kits.Assassin;
import fusion.kits.Endermage;
import fusion.kits.Fisherman;
import fusion.kits.Gladiator;
import fusion.kits.Heavy;
import fusion.kits.Hulk;
import fusion.kits.Kangaroo;
import fusion.kits.Monk;
import fusion.kits.Neo;
import fusion.kits.Ninja;
import fusion.kits.PVP;
import fusion.kits.Phantom;
import fusion.kits.Sanic;
import fusion.kits.Shark;
import fusion.kits.Snail;
import fusion.kits.SpellCaster;
import fusion.kits.Spiderman;
import fusion.kits.Stalker;
import fusion.kits.Stomper;
import fusion.kits.Switcher;
import fusion.kits.Thor;
import fusion.kits.Turtle;
import fusion.kits.Vampire;
import fusion.kits.Vigilante;
import fusion.kits.Viper;
import fusion.kits.Wimp;
import fusion.kits.listeners.AnchorEvent;
import fusion.kits.listeners.AssassinEvent;
import fusion.kits.listeners.EndermageEvent;
import fusion.kits.listeners.FishEvent;
import fusion.kits.listeners.GladiatorEvent;
import fusion.kits.listeners.HulkEvent;
import fusion.kits.listeners.KangarooEvent;
import fusion.kits.listeners.MonkEvent;
import fusion.kits.listeners.NeoEvent;
import fusion.kits.listeners.NinjaEvent;
import fusion.kits.listeners.PhantomEvent;
import fusion.kits.listeners.SharkEvent;
import fusion.kits.listeners.SnailEvent;
import fusion.kits.listeners.SpellCasterEvent;
import fusion.kits.listeners.SpidermanEvent;
import fusion.kits.listeners.StalkerEvent;
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
import fusion.kits.utils.kitutils.GladiatorArena;
import fusion.kits.utils.kitutils.GladiatorManager;
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
import fusion.listeners.PlayerInteractSign;
import fusion.listeners.PlayerJoin;
import fusion.listeners.PlayerMove;
import fusion.listeners.PlayerQuit;
import fusion.listeners.PlayerRespawn;
import fusion.listeners.PlayerTeleport;
import fusion.listeners.TabComplete;
import fusion.listeners.WeatherChange;
import fusion.teams.cmds.TeamCommand;
import fusion.teams.utils.TeamManager;
import fusion.utils.ConfigManager;
import fusion.utils.EventModeHandler;
import fusion.utils.Settings;
import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.command.CommandFramework;
import fusion.utils.duels.cmds.SetDuelSpawn;
import fusion.utils.editing.Bounds;
import fusion.utils.editing.EditorManager;
import fusion.utils.editing.TeleportListener;
import fusion.utils.editing.ToolClick;
import fusion.utils.editing.cmds.RegionCreate;
import fusion.utils.editing.cmds.RegionDelete;
import fusion.utils.editing.cmds.RegionList;
import fusion.utils.editing.cmds.SetFlag;
import fusion.utils.editing.editors.RegionEditor;
import fusion.utils.editing.regions.RegionManager;
import fusion.utils.protection.BlockBreak;
import fusion.utils.protection.BlockBurn;
import fusion.utils.protection.BlockDecay;
import fusion.utils.protection.BlockIgnite;
import fusion.utils.protection.BlockPlace;
import fusion.utils.protection.PlayerDamage;
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
	private ConfigManager spawn, warps, regions, config, kitInfo, teams, defaultPlayerFile;
	
	private EventModeHandler eventModeHandler; // this is for non-automated events essentially
	private EventHandler eventHandler; // this is for the cool automated events
	
	public void onEnable() {

		long startTime = System.nanoTime();

		instance = this;
		framework = new CommandFramework(this);
		
		spawn = new ConfigManager("spawn", null);
		warps = new ConfigManager("warps", null);
		regions = new ConfigManager("regions", null);
		config = new ConfigManager("config", null);
		kitInfo = new ConfigManager("kit_info", null);
		teams = new ConfigManager("teams", null);
		defaultPlayerFile = new ConfigManager("default_player_data", null);
		
		// registerEntity(CandyMan.class, "Candyman", 120);
		
		Settings.getSettings().initSettings();
		
		log("Instance & framework created");

		loadListeners(new AsyncPlayerChat(), new InventoryClick(), new PlayerTeleport(), new TeleportListener(), new PlayerInteract(),
				new FoodChange(), new FishEvent(), new StomperEvent(), new ViperEvent(), new PlayerDeath(),
				new PlayerJoin(), new PlayerQuit(), new PlayerRespawn(), new EntityDamageByEntity(), new ItemPickup(),
				new BlockPlace(), new BlockBreak(), new ToolClick(), new RegionEditor(), new PlayerDamage(),
				new DropItem(), new MobSpawn(), new BlockIgnite(), new BlockDecay(), new BlockBurn(), new ThorEvent(),
				new TabComplete(), new ChunkUnload(), new ChunkLoad(), new PlayerInteractEntity(), new SwitchEvent(), new CommandPreprocess(), new SnailEvent(), new NinjaEvent(), new SharkEvent(),
				new GladiatorEvent(), new PlayerMove(), new WimpEvent(),
				new SpellCasterEvent(), new TurtleEvent(), new VampireEvent(), new VigilanteEvent(), new KangarooEvent(), new EndermageEvent(), new WeatherChange(), 
				new MonkEvent(), new HulkEvent(), new PhantomEvent(), new StalkerEvent(), new PlayerInteractSign(), new AnchorEvent(), new SpidermanEvent(), new NeoEvent(), new AssassinEvent());

		log("Listeners loaded");

		loadCommands(new KitCommand(), new WarpCreate(), new WarpList(), new SetSpawn(), new SpawnCommand(),
				new RegionCreate(), new RegionList(), new SetFlag(), new WarpDelete(), new RegionDelete(),
				new Balance(), new CombatLogCommand(), new ClearKit(), new CandyManCommands(), new EcoSet(), new EcoGive(), new Pay(), 
				new TeamCommand(), new Stats(), new Help(), new SetVigilante(), new SetDuelSpawn(), new EventModeCommands(), new EventCommands());

		log("Commands loaded");

		loadKits(new PVP(), new Archer(), new Fisherman(), new Stomper(), new Viper(), new Heavy(), new Thor(),
				new Switcher(), new Sanic(), new Shark(), new Ninja(), new Snail(), new Gladiator(),
				new Wimp(), new SpellCaster(), new Vampire(), new Turtle(), new Vigilante(), new Kangaroo(), 
				new Endermage(), new Monk(), new Hulk(), new Phantom(), new Stalker(), new Anchor(), new Spiderman(), new Neo(), new Assassin());

		log("Kits loaded");

		Spawn.getInstance().load();

		log("Spawn loaded");

		WarpManager.getInstance().loadWarps();

		log("Warps loaded");

		TeamManager.get().loadTeams();

		log("Teams loaded");
		
		registerPlaceHolders();

		EditorManager.getInstance().loadEditors();

		RegionManager.getInstance().loadRegions();
		
		ConfigurationSerialization.registerClass(Bounds.class);
		
		if (Bukkit.getOnlinePlayers().size() != 0) {

			for (Player online : Bukkit.getOnlinePlayers()) {

				try {
					mKitUser.getInstance(online).load();
					mKitUser.getInstance(online).clearKit();
					Spawn.getInstance().forceTP(online);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
		
		Utils.get().load();
		
		this.eventModeHandler = new EventModeHandler();
		this.eventHandler = new EventHandler();
		
		long finishTime = System.nanoTime();

		log("Finished in: " + TimeUnit.NANOSECONDS.toMillis(finishTime - startTime) + " ms");

	}

	public void onDisable() {

		TeamManager.get().saveTeams();

		log("Teams saved");

		KitManager.getInstance().unloadKits();

		Spawn.getInstance().save();
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			
			mKitUser.getInstance(online).save();
			
			if (CombatLog.getInstance().isInCombat(online)) {
				CombatLog.getInstance().remove(online);
			}
		}

		WarpManager.getInstance().saveWarps();
		
		RegionManager.getInstance().saveRegions();
		
		if (GladiatorManager.getInstance().getArenas() != null && !GladiatorManager.getInstance().getArenas().isEmpty())  {
			for (GladiatorArena arena : GladiatorManager.getInstance().getArenas()) {
				arena.destroyArena();
			}
		}
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
					return "None";
				}

				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);

				if (user.getKit() == null) {
					return "None";
				}

				return String.valueOf(user.getKit().getName());

			}
		});

		// current team
		PlaceholderAPI.registerPlaceholder(this, "fusion_team", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

				if (event.getPlayer() == null) {
					return "None";
				}

				Player player = event.getPlayer();

				mKitUser user = mKitUser.getInstance(player);

				if (user.getTeam() == null) {
					return "None";
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

							sb.append(ChatColor.WHITE).append(tplayer.getName()).append(", ");

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

				return ChatColor.RED + String.valueOf("Combat: " + CombatLog.getInstance().getRemainingTime(player));

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
				
				return user.getKDRText();

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
	
	public ConfigManager getDefaultPlayerFile() {
		return defaultPlayerFile;
	}
	
	public EventModeHandler getEventModeHandler() {
		return eventModeHandler;
	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
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
	
	public CommandFramework getCommandFramework() {
		return framework;
	}

	private void log(String s) {

		System.out.println("[Fusion] " + s);

	}

}
