package fusion.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.kits.utils.kitutils.GladiatorManager;
import fusion.main.Fusion;
import fusion.teams.utils.Team;
import fusion.teams.utils.TeamManager;
import fusion.utils.editing.regions.ProtectedRegion.HealingItem;
import fusion.utils.spawn.Spawn;

/**
 * 
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class mKitUser {

	static Set<mKitUser> instances = new HashSet<mKitUser>();

	// add setters and getters here - do not make the variables public!

	private Player player;
	private ConfigManager file;
	private Set<Kit> ownedKits = new HashSet<Kit>();
	private Kit kit, previousKit;
	private double money;
	private HealingItem item;
	
	private int kills;
	private int deaths;
	private int killstreak;

	private mKitUser(Player player) {

		this.player = player;
		this.kit = null;
		
		instances.add(this);

	}

	public static mKitUser getInstance(Player player) {

		for (mKitUser users : instances) {

			if (users.getPlayer().getName().equalsIgnoreCase(player.getName()))
				return users;

		}

		return new mKitUser(player);

	}

	public Team getTeam() {

		for (Team t : TeamManager.get().getTeams()) {

			if (t.getMembers().keySet().contains(player.getUniqueId())) {

				return t;

			}

		}

		return null;

	}

	public Player getPlayer() {
		return player;
	}
	
	public ConfigManager getPlayerFile() {
		return file;
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}

	public Kit getKit() {
		return kit;
	}

	public boolean hasKit() {
		return kit != null;
	}

	public void setPreviousKit(Kit kit) {
		this.previousKit = kit;
	}

	public Kit getPreviousKit() {
		return previousKit;
	}

	public boolean hasPreviousKit() {
		return previousKit != null;
	}

	public boolean ownsKit(Kit kit) {
		return ownedKits.contains(kit);
	}

	public void addOwnedKit(Kit kit) {
		ownedKits.add(kit);
	}

	public double getCandies() {
		return money;
	}

	public void setCandies(double candies) {
		this.money = candies;
	}

	public void addCandies(double candies) {
		this.money += candies;
	}

	public void removeCandies(double candies) {
		this.money -= candies;
	}

	public HealingItem getHealingItem() {
		return item;
	}

	public void setHealingItem(HealingItem item) {
		this.item = item;
	}
	
	public boolean isInGladiatorArena() {
		return GladiatorManager.getInstance().getArena(player) != null;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public void clearKit() {
		
		Player player = getPlayer();
		
		setKit(null);
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		Utils.giveDefaultItems(player);
		
		Spawn.getInstance().teleport(player);
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			
			player.removePotionEffect(effect.getType());
			
		}
		
	}

	public void load() throws IOException {
		
		this.file = new ConfigManager(player.getUniqueId().toString(), "players");
		
		BufferedReader reader = new BufferedReader(new FileReader(file.getFile()));  
		
		if (reader.readLine() == null) { // assumes that if the balance is missing, everything else probably is too. If not, overwrite all the other values anyway :P
			
			try {
				file.setFile(ConfigManager.transferData(Fusion.getInstance().getDefaultPlayerFile(), file)); // should copy defaults into the empty player file
			} catch (IOException e) {
				e.printStackTrace();
				player.kickPlayer("Profile did not load correctly, please rejoin.");
			}
			
			reader.close();
			
		}
		
		// assume at this point that all values are now there because of the check above 
		
		setCandies(file.getDouble("profile.candies"));
		
		setHealingItem(HealingItem.valueOf(file.getString("settings.healingItem").toUpperCase()));
		
		if (!file.getList("kits").isEmpty()) {
			for (String kits : file.getStringList("kits")) {
				
				String kitNames = kits;
				
				ownedKits.add(KitManager.getInstance().valueOf(kitNames));
				
			}
			
		}
		
		setKills(file.getInt("kills"));
		
		setDeaths(file.getInt("deaths"));
		
		setKillStreak(file.getInt("killstreak"));
		
	}
	
	public void save() {

		if (!ownedKits.isEmpty()) {

			List<String> kitList = new ArrayList<String>();

			for (Kit kitz : ownedKits) {

				kitList.add(kitz.getName());

			}
			
			file.set("kits", kitList);
			
		}
		
		file.set("profile.candies", getCandies());
		file.set("settings.healingItem", item.toString());
		file.set("kills", getKills());
		file.set("deaths", getDeaths());
		file.set("killstreak", getKillStreak());
		
	}

	public void unload() {
		ownedKits.clear();
		kit = null;
		previousKit = null;
	}

	public void addDeath() {
		deaths += 1;
	}

	public void addKill() {
		kills += 1;
	}

	public int getKillStreak() {
		return killstreak;
	}

	public void setKillStreak(int killstreak) {
		this.killstreak = killstreak;
	}

	public void addKillStreak() {
		killstreak += 1;
	}

	public void resetKillStreak() {
		killstreak = 0;
	}
	
	public double getKDR() {
		double kd = 0.0;
		
		if (getDeaths() == 0) {
			
			kd = getKills(); // no divide by 0 errors
			
		} else {
			kd = (double) getKills() / (double) getDeaths();
		}
		
		return kd;
	}
	
	public String getKDRText() {
		DecimalFormat dm = new DecimalFormat("#.##");
		String kdr = dm.format(getKDR());
		return kdr;
	}
}
