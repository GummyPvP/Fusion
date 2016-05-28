package fusion.utils.protection;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fusion.utils.ConfigManager;
import fusion.utils.ItemBuilder;

/**
 * 
 * Copyright GummyPvP. Created on May 27, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class ProtectedRegion extends Region {

	Bounds bounds;
	String name;
	
	boolean pvpEnabled = true;
	HealingItem item = HealingItem.SOUP;
	boolean refills = false;
	
	public ProtectedRegion(String name, World world, Vector min, Vector max) {
		
		this.bounds = new Bounds(world, min, max);
		this.name = name;
	}
	
	public Bounds getBounds() {
		
		return bounds;
		
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void togglePVP(boolean enabled) {
		
		this.pvpEnabled = enabled;
		
	}
	
	public void toggleRefills(boolean enabled) {
		
		this.refills = enabled;
		
	}
	
	public void setHealthItem(HealingItem item) {
		
		this.item = item;
		
	}
	
	public boolean isPVPEnabled() {
		return pvpEnabled;
	}
	
	public boolean areRefillsAllowed() {
		return refills;
	}
	
	public HealingItem getHealingItem() {
		return item;
	}
	
	@Override
	public void save() {
		
		ConfigManager.getRegionsFile().set("regions." + getName() + ".world", getBounds().getWorld().getName());
		ConfigManager.getRegionsFile().set("regions." + getName() + ".minPoint", getBounds().getMin());
		ConfigManager.getRegionsFile().set("regions." + getName() + ".maxPoint", getBounds().getMax());
		ConfigManager.getRegionsFile().set("regions." + getName() + ".pvpEnabled", pvpEnabled);
		ConfigManager.getRegionsFile().set("regions." + getName() + ".refills", refills);
		ConfigManager.getRegionsFile().set("regions." + getName() + ".healthItem", item.toString());
		
	}
	
	public enum HealingItem {
		
		ANY(),
		SOUP(new ItemBuilder(Material.MUSHROOM_SOUP).name("&bSoup").lore("Drinking this soup heals you 3.5 hearts").build()),
		POTION(new ItemBuilder(Material.POTION).name("&bPotion").lore(Arrays.asList("Throw this to heal yourself... or maybe", "even the person you're fighting!")).durability(16421).build()),
		GAPPLE(new ItemBuilder(Material.GOLDEN_APPLE).name("&bGapple").lore("Eat this to have a short burst of regen.").durability(1).build());
		
		ItemStack item;
		
		HealingItem() { }
		
		HealingItem(ItemStack item) {
			
			this.item = item;
			
		}
		
		public ItemStack getItem() {
			
			return item;
			
		}
		
	}
}
