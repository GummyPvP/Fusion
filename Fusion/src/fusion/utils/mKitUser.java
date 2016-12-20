package fusion.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.teams.utils.Team;
import fusion.teams.utils.TeamManager;
import fusion.utils.multiplier.Multiplier;
import fusion.utils.multiplier.MultiplierManager;
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
	private Set<Kit> ownedKits = new HashSet<Kit>();
	private Kit kit, previousKit;
	private double money;
	private HealingItem item;
	private Multiplier multiplier;
	private boolean glad;

	private int kills;
	private int deaths;

	private mKitUser(Player player) {

		this.player = player;
		this.kit = null;
		setGlad(false);
		multiplier = Multiplier.NONE;

		instances.add(this);

		// done after because method gets instance of the players mkitUser class
		MultiplierManager.getInstance().updateMultiplier(player);

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

	public Multiplier getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(Multiplier type) {
		multiplier = type;
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

		if (Fusion.getInstance().getPlayerFile(player.getName()).contains("profile.candies")) {

			setCandies(Fusion.getInstance().getPlayerFile(player.getName()).getDouble("profile.candies"));

		}

		if (Fusion.getInstance().getPlayerFile(player.getName()).contains("settings.healingItem")) {

			setHealingItem(HealingItem.valueOf(Fusion.getInstance().getPlayerFile(player.getName())
					.getString("settings.healingItem").toUpperCase()));

		} else
			setHealingItem(HealingItem.SOUP);

		if (Fusion.getInstance().getPlayerFile(player.getName()).getList("kits") == null)
			return;

		for (String kits : Fusion.getInstance().getPlayerFile(player.getName()).getStringList("kits")) {

			String kitNames = kits;

			ownedKits.add(KitManager.getInstance().valueOf(kitNames));

		}
		if (Fusion.getInstance().getPlayerFile(player.getName()).contains("kills")) {
			
			kills = Fusion.getInstance().getPlayerFile(player.getName()).getInt("kills");
			
		} else {
			
			kills = 0;
			
		}
		if (Fusion.getInstance().getPlayerFile(player.getName()).contains("deaths")) {
			
			deaths = Fusion.getInstance().getPlayerFile(player.getName()).getInt("deaths");
			
		} else {
			
			deaths = 0;
			
		}

	}

	public void save() {

		if (!ownedKits.isEmpty()) {

			List<String> kitList = new ArrayList<String>();

			for (Kit kitz : ownedKits) {

				kitList.add(kitz.getName());

			}

			Fusion.getInstance().getPlayerFile(player.getName()).set("kits", kitList);

		}

		Fusion.getInstance().getPlayerFile(player.getName()).set("profile.candies", getCandies());

		Fusion.getInstance().getPlayerFile(player.getName()).set("settings.healingItem", item.toString());
		
		Fusion.getInstance().getPlayerFile(player.getName()).set("kills", getKills());
		Fusion.getInstance().getPlayerFile(player.getName()).set("deaths", getDeaths());

	}

	public void unload() {

		ownedKits.clear();
		kit = null;
		previousKit = null;

	}

	public boolean isGlad() {
		return glad;
	}

	public void setGlad(boolean glad) {
		this.glad = glad;
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

	public void addDeath() {

		deaths = getDeaths() + 1;

	}

	public void addKill() {

		kills = getKills() + 1;

	}
}
