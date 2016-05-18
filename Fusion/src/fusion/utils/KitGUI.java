package fusion.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.Kit;
import fusion.kits.KitManager;
import klap.utils.mPlayer;

/**
	 * 
	 * Copyright GummyPvP. Created on May 17, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class KitGUI {
	
	private Inventory inv;
	public static final String INVENTORY_NAME = Chat.IMPORTANT_COLOR + "GummyPvP - Kits";
	
	public KitGUI(Player player) {
		
		this.inv = Bukkit.createInventory(player, ensureSize(KitManager.getInstance().getKits().size()) + 18, INVENTORY_NAME);
		populateInventory(player);
		player.openInventory(inv);
		
	}
	
	// ensures that we use enough slots to hold all the kit items
	private int ensureSize(int size) {
		
		if ((size + 18) % 9 == 0) return size;
		
		return ensureSize(++size);
		
	}
	
	private void populateInventory(Player player) {
		
		mPlayer user = mPlayer.getInstance(player);
		
		ItemStack glass = new ItemBuilder(Material.THIN_GLASS).name(" ").build();
		
		for (int i = 0; i < 9; i++) {
			
			inv.setItem(i, glass);
			
		}
		
		for (int i = (inv.getSize() - 9); i < inv.getSize(); i++) {
			
			inv.setItem(i, glass);
			
		}
		
		for (Kit kits : KitManager.getInstance().getKits()) {
			
			if (!user.ownsKit(kits) && !kits.isDefault()) continue;
			
			inv.addItem(createKitItem(kits));
			continue;
			
		}
		
	}
	
	public Inventory getInventory() {
		
		return inv;
		
	}
	
	private ItemStack createKitItem(Kit kit) {
		
		ItemStack item = kit.getInventoryItem();
		
		List<String> items = new ArrayList<String>();
		
		for (ItemStack allItems : kit.getItems()) {
			
			String tempName = allItems.getType().toString().toLowerCase().replaceAll("_", " ");
			String firstChar = tempName.substring(0, 1).toUpperCase();
			String name = firstChar + tempName.substring(1);
			
			items.add("&8[&a+&8] " + Chat.BASE_COLOR + name);
			
		}
		
		for (ItemStack armor : kit.getArmor()) {
			
			String tempName = armor.getType().toString().toLowerCase().replaceAll("_", " ");
			String firstChar = tempName.substring(0, 1).toUpperCase();
			String name = firstChar + tempName.substring(1);
			
			items.add("&8[&a+&8] " + Chat.STAFF_NOTIFICATION + name);
			
		}
		
		for (PotionEffect effects : kit.getPotionEffects()) {
			
			String tempName = effects.getType().getName().toLowerCase().replaceAll("_", " ");
			String firstChar = tempName.substring(0, 1).toUpperCase();
			String name = firstChar + tempName.substring(1);
			
			items.add("&8[&a+&8] " + Chat.IMPORTANT_COLOR + name + " " + (effects.getAmplifier() + 1));
			
		}
		
		return new ItemBuilder(item).name(Chat.SECONDARY_BASE + kit.getName()).lore(items).build();
		
	}
}
