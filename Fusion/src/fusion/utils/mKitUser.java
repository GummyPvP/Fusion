package fusion.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.utils.protection.ProtectedRegion.HealingItem;

/**
	 * 
	 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class mKitUser {
	
	static Set<mKitUser> instances = new HashSet<mKitUser>();
	
	Player player;
	Kit kit, previousKit;
	Set<Kit> ownedKits = new HashSet<Kit>();
	double money;
	HealingItem item;
	
	private mKitUser(Player player) {
		
		this.player = player;
		this.kit = null;
		instances.add(this);
		
	}
	
	public static mKitUser getInstance(Player player) {
		
		for (mKitUser users : instances) {
			
			if (users.getPlayer().getName().equalsIgnoreCase(player.getName())) return users;
			
		}
		
		return new mKitUser(player);
		
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
		
		if (ConfigManager.getPlayerFile(player.getName()).contains("profile.candies")) {
			
			setCandies(ConfigManager.getPlayerFile(player.getName()).getDouble("profile.candies"));
			
		}
		
		if (ConfigManager.getPlayerFile(player.getName()).contains("settings.healingItem")) {
			
			setHealingItem(HealingItem.valueOf(ConfigManager.getPlayerFile(player.getName()).getString("settings.healingItem").toUpperCase()));
			
		} else setHealingItem(HealingItem.SOUP);
		
		if (ConfigManager.getPlayerFile(player.getName()).getList("kits") == null) return;
		
		for (String kits : ConfigManager.getPlayerFile(player.getName()).getStringList("kits")) {
			
			String kitNames = kits;
			
			ownedKits.add(KitManager.getInstance().valueOf(kitNames));
			
		}
		
	}
	
	public void save() {
		
		if (!ownedKits.isEmpty()) {
			
			List<String> kitList = new ArrayList<String>();
			
			for (Kit kitz : ownedKits) {
				
				kitList.add(kitz.getName());
				
			}
			
			ConfigManager.getPlayerFile(player.getName()).set("kits", kitList);
			
		}
		
		ConfigManager.getPlayerFile(player.getName()).set("profile.candies", getCandies());
		
		ConfigManager.getPlayerFile(player.getName()).set("settings.healingItem", item.toString());
		
	}
	
	public void unload() {
		
		ownedKits.clear();
		kit = null;
		previousKit = null;
		
	}
}
