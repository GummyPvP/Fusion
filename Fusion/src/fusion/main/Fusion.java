package fusion.main;

import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import be.maximvdw.placeholderapi.*;
import fusion.cmds.*;
import fusion.kits.*;
import fusion.kits.listeners.*;
import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.kits.utils.kitutils.GladiatorManager;
import fusion.listeners.*;
import fusion.teams.cmds.TeamCommand;
import fusion.teams.utils.TeamManager;
import fusion.utils.*;
import fusion.utils.Settings;
import fusion.utils.command.CommandFramework;
import fusion.utils.editing.*;
import fusion.utils.editing.cmds.*;
import fusion.utils.editing.editors.RegionEditor;
import fusion.utils.protection.*;
import fusion.utils.spawn.Spawn;
import fusion.utils.warps.*;

/**
 * 
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Fusion extends JavaPlugin {

	private static Fusion instance;

	private CommandFramework framework;
	private ConfigManager spawn, warps, regions, config, kitInfo, teams;
	
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
		
		// registerEntity(CandyMan.class, "Candyman", 120);
		
		Settings.getSettings().initSettings();
		
		log("Instance & framework created");

		loadListeners(new AsyncPlayerChat(), new InventoryClick(), new PlayerTeleport(), new TeleportListener(), new PlayerInteract(),
				new FoodChange(), new FishEvent(), new StomperEvent(), new ViperEvent(), new PlayerDeath(),
				new PlayerJoin(), new PlayerQuit(), new PlayerRespawn(), new EntityDamageByEntity(), new ItemPickup(),
				new BlockPlace(), new BlockBreak(), new ToolClick(), new RegionEditor(), new PlayerDamage(),
				new DropItem(), new MobSpawn(), new BlockIgnite(), new BlockDecay(), new BlockBurn(), new ThorEvent(),
				new TabComplete(), new ChunkUnload(), new ChunkLoad(), new PlayerInteractEntity(), new SwitchEvent(),
				new EndermageEvent(), new CommandPreprocess(), new SnailEvent(), new NinjaEvent(), new SharkEvent(),
				new GladiatorEvent(), new PlayerMove(), new WimpEvent(),
				new SpellCasterEvent(), new TurtleEvent(), new VampireEvent(), new VigilanteEvent());

		log("Listeners loaded");

		loadCommands(new KitCommand(), new WarpCreate(), new WarpList(), new SetSpawn(), new SpawnCommand(),
				new RegionCreate(), new RegionList(), new SetFlag(), new WarpDelete(), new RegionDelete(),
				new Balance(), new CombatLogCommand(), new ClearKit(), new CandyManCommands(), new EcoSet(), new EcoGive(), new Pay(), new TeamCommand(), new Stats(), new Help(), new SetVigilante());

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
		
		StatsManager.getInstance().startScoreboard(this);
		
		ConfigurationSerialization.registerClass(Bounds.class);
		
		if (Bukkit.getOnlinePlayers().size() != 0) {

			for (Player online : Bukkit.getOnlinePlayers()) {

				mKitUser.getInstance(online).load();

			}

		}
		
		Utils.get().load();
		
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
		
		for (GladiatorArena arena : GladiatorManager.getInstance().getArenas()) {
			arena.destroyArena();
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
