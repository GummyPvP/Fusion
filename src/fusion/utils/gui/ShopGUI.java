package fusion.utils.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.utils.ItemBuilder;
import fusion.utils.Settings;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;

/**
 * 
 * Copyright GummyPvP. Created on May 17, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class ShopGUI {

	private Inventory inv;
	public static String INVENTORY_NAME = ChatColor.translateAlternateColorCodes('&', Settings.getSettings().SHOP_GUI_NAME);
	private static Map<Player, Integer> page = new HashMap<Player, Integer>();

	public ShopGUI(Player player) {
		this(player, 0);
	}

	public ShopGUI(Player player, int page) {
		
		player.closeInventory();
		
		if (mKitUser.getInstance(player).getKits().size() >= KitManager.getInstance().getPaidKits().size()) {
			
			Chat.getInstance().messagePlayer(player, "&cYou already own all kits!");
			
			return;
		}
		
		this.inv = Bukkit.createInventory(player, ensureSize(KitManager.getInstance().getKits().size()) + 18, INVENTORY_NAME + " - Page: " + (page + 1));
		populateInventory(player, page);
		
		player.openInventory(inv);
		
		ShopGUI.page.put(player, page);

	}

	// ensures that we use enough slots to hold all the kit items
	private int ensureSize(int size) {

		if (size >= 36)
			return 36;

		if ((size + 18) % 9 == 0)
			return size;

		return ensureSize(++size);

	}

	private void populateInventory(Player player, int page) {

		mKitUser user = mKitUser.getInstance(player);

		ItemStack glass = new ItemBuilder(Material.GLASS_PANE).name(" ").build();

		// Glass and other stuff
		
		for (int i = 0; i < 9; i++) {

			inv.setItem(i, glass);

		}

		for (int i = (inv.getSize() - 9); i < inv.getSize(); i++) {

			inv.setItem(i, glass);
			
		}
		
		if (page > 0) {
			
			inv.setItem(inv.getSize() - 9, new ItemBuilder(Material.IRON_INGOT).name("&a<--").lore("Previous page").build());
			
		}
		
		// end
		
		// future pages check
		
		List<Kit> checkedKits = KitManager.getInstance().getKits().subList(page * 36, (page * 36) + ensureKits(KitManager.getInstance().getKits().size() - (page * 36)));
		
		try {
			
			List<Kit> futureCheck = KitManager.getInstance().getKits().subList((page + 1) * 36, ((page + 1) * 36) + ensureKits(KitManager.getInstance().getKits().size() - ((page + 1) * 36)));
			
			if (!futureCheck.isEmpty()) inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.GOLD_INGOT).name("&a-->").lore("Next page").build());
			
		} catch (IllegalArgumentException ignored) { } // intentionally blank
		
		// end
		
		// use 36 because that is how many kits should be presented in the inventory (the inventory will hold 54 slots, it allows us to hold 18 other items)
		
		for (Kit kits : checkedKits) {
			
			if (user.ownsKit(kits) || kits.isDefault())
				continue;
			
			inv.addItem(createKitItem(kits));
			
		}

	}

	public Inventory getInventory() {

		return inv;

	}
	
	private int ensureKits(int size) {
		
		return (size >= 36 ? 36 : size);
		
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
		
		if (kit.getPotionEffects() != null) {
			
			for (PotionEffect effects : kit.getPotionEffects()) {

				String tempName = effects.getType().getName().toLowerCase().replaceAll("_", " ");
				String firstChar = tempName.substring(0, 1).toUpperCase();
				String name = firstChar + tempName.substring(1);

				items.add("&8[&a+&8] " + Chat.IMPORTANT_COLOR + name + " " + (effects.getAmplifier() + 1));

			}
			
		}
		
		if (kit.getSpecialAdvantageStrings() != null) {
			
			for (String strings : kit.getSpecialAdvantageStrings()) {
				
				items.add(ChatColor.GOLD + strings);
				
			}
			
		}
		
		if (kit.getCost() != 0.0) {
			
			items.add("&8[&a+&8] " + ChatColor.DARK_AQUA + "Cost: " + kit.getCost() + " candies");
			
		}
		
		return new ItemBuilder(item).name(Chat.SECONDARY_BASE + kit.getName()).lore(items).build();

	}
	
	public static int getPage(Player player) {
		
		return page.get(player);
		
	}
}
