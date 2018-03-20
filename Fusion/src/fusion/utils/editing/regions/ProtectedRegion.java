package fusion.utils.editing.regions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fusion.kits.utils.Kit;
import fusion.main.Fusion;
import fusion.utils.ItemBuilder;
import fusion.utils.editing.Bounds;

/**
 * 
 * Copyright GummyPvP. Created on May 27, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class ProtectedRegion extends Region {

	private Bounds bounds;
	private String name;

	private boolean pvpEnabled = true;
	private HealingItem item = HealingItem.SOUP;
	private boolean refills = false;
	private Set<Kit> blockedKits = new HashSet<Kit>();
	private final RegionTracker tracker;

	public ProtectedRegion(String name, World world, Vector min, Vector max) {

		this.bounds = new Bounds(world, min, max);
		this.name = name;

		tracker = new RegionTracker(this);
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

	public void addBlockedKit(Kit kit) {
		blockedKits.add(kit);
	}

	public void removeBlockedKit(Kit kit) {
		blockedKits.remove(kit);
	}

	public Set<Kit> getBlockedKits() {
		return blockedKits;
	}

	@Override
	public void save() {

		Fusion.getInstance().getRegionsFile().set("regions." + getName() + ".world", getBounds().getWorld().getName());
		Fusion.getInstance().getRegionsFile().set("regions." + getName() + ".minPoint", getBounds().getMin());
		Fusion.getInstance().getRegionsFile().set("regions." + getName() + ".maxPoint", getBounds().getMax());
		Fusion.getInstance().getRegionsFile().set("regions." + getName() + ".pvpEnabled", pvpEnabled);
		Fusion.getInstance().getRegionsFile().set("regions." + getName() + ".refills", refills);
		Fusion.getInstance().getRegionsFile().set("regions." + getName() + ".healthItem", item.toString());

		if (blockedKits.isEmpty())
			return;

		List<String> blockedList = new ArrayList<String>();

		for (Kit kit : blockedKits) {

			blockedList.add(kit.getName());

		}

		Fusion.getInstance().getRegionsFile().set("regions." + getName() + ".blockedKits", blockedList);

	}

	public void register() {
		Bukkit.getPluginManager().registerEvents(this.tracker, Fusion.getInstance());
	}

	public void unregister() {
		HandlerList.unregisterAll(this.tracker);
	}

	public enum HealingItem {

		ANY(), SOUP(
				new ItemBuilder(Material.MUSHROOM_SOUP).name("&bSoup").lore("Drinking this soup heals you 3.5 hearts")
						.build()), POTION(new ItemBuilder(Material.POTION).name("&bPotion").lore(Arrays
								.asList("Throw this to heal yourself... or maybe", "even the person you're fighting!"))
								.durability(16421).build());

		ItemStack item;

		HealingItem() {
		}

		HealingItem(ItemStack item) {

			this.item = item;

		}

		public ItemStack getItem() {

			return item;

		}

	}
}
