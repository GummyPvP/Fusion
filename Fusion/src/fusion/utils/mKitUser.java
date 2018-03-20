package fusion.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.kits.utils.kitutils.GladiatorManager;
import fusion.teams.utils.Team;
import fusion.teams.utils.TeamManager;
import fusion.utils.protection.ProtectedRegion.HealingItem;

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
		
		this.file = new ConfigManager(player.getUniqueId().toString(), "players");
		
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

		for (PotionEffect effect : player.getActivePotionEffects()) {

			player.removePotionEffect(effect.getType());

		}

	}

	public void load() {
		
		if (file == null) {
			
			player.kickPlayer(ChatColor.RED + "Your profile did not load correctly, please rejoin. If the problem persists, contact a staff member via the forums.");
			
			return;
		}
		
		if (file.contains("profile.candies")) {

			setCandies(file.getDouble("profile.candies"));

		} else setCandies(100.0);

		if (file.contains("settings.healingItem")) {

			setHealingItem(HealingItem.valueOf(file
					.getString("settings.healingItem").toUpperCase()));

		} else
			setHealingItem(HealingItem.SOUP);

		if (file.getList("kits") == null)
			return;

		for (String kits : file.getStringList("kits")) {

			String kitNames = kits;

			ownedKits.add(KitManager.getInstance().valueOf(kitNames));

		}
		
		if (file.contains("kills")) {

			setKills(file.getInt("kills"));

		} else {

			setKills(0);

		}
		
		if (file.contains("deaths")) {

			setDeaths(file.getInt("deaths"));

		} else {

			setDeaths(0);

		}
		
		if (file.contains("killstreak")) {
			
			setKillStreak(file.getInt("killstreak"));
			
		} else {
			
			setKillStreak(0);
			
		}

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

		deaths = getDeaths() + 1;

	}

	public void addKill() {

		kills = getKills() + 1;

	}

	public int getKillStreak() {
		return killstreak;
	}

	public void setKillStreak(int killstreak) {
		this.killstreak = killstreak;
	}

	public void addKillStreak() {

		killstreak = getKillStreak() + 1;

	}

	public void resetKillStreak() {

		killstreak = 0;

	}
}
