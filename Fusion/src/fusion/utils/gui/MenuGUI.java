package fusion.utils.gui;

/*
 * 
 * Copyright GummyPvP. Created on Jun 11, 2016 by Jeremy Gooch.
 * All Rights Reserved.
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/**
 * @author NonameSLdev
 */
public abstract class MenuGUI implements Listener {
	
	private String name;
	private int size;
	private Plugin plugin;
	private ArrayList<Inventory> inv;
	private ItemStack pageBack;
	private ItemStack pageForward;
	private boolean hasPages;

	public MenuGUI(String name, int size, Plugin plugin, boolean hasPages) {
		this.name = name;
		this.size = size;
		this.hasPages = hasPages;
		this.plugin = plugin;
		this.inv = new ArrayList<>();
		if (hasPages && size < 9)
			size = 18;
		this.inv.add(Bukkit.createInventory(null, size, name));
		if (hasPages) {
			addBackwardsItem(0);
			addForwardItem(0);
			addPageIndicator(0);
		}
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public MenuGUI(Player player, String name, int size, Plugin plugin, boolean hasPages) {
		
		this.name = name;
		this.size = size;
		this.hasPages = hasPages;
		this.plugin = plugin;
		this.inv = new ArrayList<>();
		if (hasPages && size < 9)
			size = 18;
		this.inv.add(Bukkit.createInventory(player, size, name));
		if (hasPages) {
			addBackwardsItem(0);
			addForwardItem(0);
			addPageIndicator(0);
		}
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}

	/**
	 * Triggered whenever a player clicks an item in one of the pages of the
	 * inventory
	 * 
	 * @param e
	 *            The event, containing all the information
	 */
	public abstract void onClick(InventoryClickEvent e);

	private void addPageIndicator(int page) {
		addOption(getPageIndicator(page), size - 5, page);
	}

	private void addBackwardsItem(int page) {
		pageBack = createNameAndLore(Material.GOLD_BLOCK, "Page Backward", "Move a page back.");
		addOption(pageBack, size - 9, page);
	}

	private void addForwardItem(int page) {
		pageForward = createNameAndLore(Material.GOLD_BLOCK, "Page Forward", "Move a page forward.");
		addOption(pageForward, size - 1, page);
	}

	/**
	 * Add a page to the menu
	 */
	public MenuGUI addPage() {
		if (!hasPages)
			return this;
		if (inv.get(inv.size() - 1).firstEmpty() == -1) {
			inv.add(Bukkit.createInventory(null, size, name));
			int last = inv.size() - 1;
			addBackwardsItem(last);
			addForwardItem(last);
			addPageIndicator(last);
		}
		return this;
	}

	/**
	 * Add a certain amount of pages
	 * 
	 * @param pages
	 *            The amount of pages to be added
	 */
	public MenuGUI addPages(int pages) {
		if (!hasPages)
			return this;
		for (int i = 0; i < pages; i++)
			addPage();
		return this;
	}

	/**
	 * @return The ItemStack which is the page indicator
	 */
	public ItemStack getPageIndicator(int page) {
		return createNameAndLore(Material.DIAMOND_BLOCK, "Page: " + page);
	}

	/**
	 * @return The ItemStack which is the item player click to go a page back
	 */
	public ItemStack getBackwardsItem() {
		return pageBack;
	}

	/**
	 * @return The ItemStack which is the item player click to go a page forward
	 */
	public ItemStack getForwardItem() {
		return pageForward;
	}

	private ItemStack createNameAndLore(Material material, String name, String... info) {
		ItemStack is = new ItemStack(material, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(info));
		is.setItemMeta(im);
		return is;
	}

	/**
	 * @return All the pages in the menu
	 */
	public ArrayList<Inventory> getPages() {
		return inv;
	}

	/**
	 * Add an item to the last page in the last spot that isn't taken
	 * 
	 * @param material
	 *            The material represented
	 * @param name
	 *            The name of the item
	 * @param info
	 *            The lore
	 */
	public MenuGUI addOption(Material material, String name, String... info) {
		addOption(material, name, -1, -1, info);
		return this;
	}

	/**
	 * Add an item to a page chosen, overriding the item in the spot (if it
	 * exists)
	 * 
	 * @param material
	 * @param name
	 * @param position
	 * @param page
	 * @param info
	 * @return
	 */
	public MenuGUI addOption(Material material, String name, int position, int page, String... info) {
		ItemStack is = createNameAndLore(material, name, info);
		if (position < 0)
			addOption(is);
		else
			addOption(is, position, page);
		return this;
	}

	/**
	 * @return The last page
	 */
	public Inventory getLastPage() {
		if (!hasPages)
			return getFirstPage();
		if (inv.get(inv.size() - 1).firstEmpty() == -1) {
			addPage();
		}
		return inv.get(inv.size() - 1);
	}

	/**
	 * @return The first page
	 */
	public Inventory getFirstPage() {
		return inv.get(0);
	}

	/**
	 * Add an option to the last page in the last avilable spot
	 * 
	 * @param is
	 *            The itemstack to be added
	 */
	public MenuGUI addOption(ItemStack is) {
		addOption(is, -1, -1);
		return this;
	}

	/**
	 * Add an option to a certain page, overriding existing itemstacks in the
	 * spot (if there is any)
	 * 
	 * @param is
	 *            The itemstack to be added
	 * @param position
	 *            The position in the inventory
	 * @param page
	 *            The page
	 */
	public MenuGUI addOption(ItemStack is, int position, int page) {
		if (Math.floor(position / 9) > 5)
			return this;
		Inventory inv;
		try {
			inv = this.inv.get(page);
		} catch (Exception e) {
			inv = getLastPage();
		}
		if (position < 0) {
			inv.addItem(is);
		} else
			inv.setItem(position, is);
		return this;
	}

	/**
	 * Remove an item from a page
	 * 
	 * @param position
	 *            The position which needs to be removed
	 * @param page
	 *            The page which the position is in
	 */

	@SuppressWarnings("deprecation")
	public MenuGUI removeOption(int position, int page) {
		try {
			this.inv.get(page).remove(position);
		} catch (Exception e) {
			getLastPage().remove(position);
		}
		return this;
	}

	/**
	 * Show the inventory to a player
	 * 
	 * @param player
	 *            The player which should see the menu
	 */
	public void show(Player player) {
		player.openInventory(getFirstPage());
	}

	/**
	 * Show the inventory to some players
	 * 
	 * @param p
	 *            The players which should see the menu
	 */
	public void show(Player... p) {
		for (Player player : p)
			show(player);
	}

	/**
	 * @return Size of each inventory
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return The plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	private boolean compareInvs(Inventory inv0, Inventory inv1) {
		for (int index = 0; index < inv0.getSize(); index++) {
			ItemStack a = inv0.getItem(index);
			ItemStack b = inv1.getItem(index);
			if (!((a == null && b == null) || a.isSimilar(b)))
				return false;
		}
		return true;
	}

	/**
	 * Navigate a page forward from the page that is currently open.
	 * 
	 * @param p
	 *            The player to navigate forward at
	 * @param current
	 *            The inventory that is currently open
	 */
	public void navigateForward(Player p, Inventory current) {
		if (compareInvs(current, getFirstPage()))
			return;
		p.closeInventory();
		for (int i = 0; i < inv.size(); i++) {
			if (compareInvs(current, inv.get(i))) {
				p.openInventory(inv.get(i + 1));
				break;
			}
		}
	}

	/**
	 * Navigate a page backward from the page that is currently open
	 * 
	 * @param p
	 *            The player to navigate backward at
	 * @param current
	 *            The inventory that is currently open
	 */
	public void navigateBackward(Player p, Inventory current) {
		if (compareInvs(current, getFirstPage()))
			return;
		p.closeInventory();
		for (int i = 0; i < inv.size(); i++) {
			if (compareInvs(current, inv.get(i))) {
				p.openInventory(inv.get(i - 1));
				break;
			}
		}
	}

	/**
	 * Only touch this if you really know what you're doing, if you want you can
	 * move this event to another Listener class and just loop thru all menus.
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!e.getInventory().getTitle().equals(name))
			return;
		if (!(e.getWhoClicked() instanceof Player))
			return;
		e.setCancelled(true);
		ItemStack is = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		if (hasPages && is != null && is.isSimilar(pageBack)) {
			navigateBackward(p, e.getInventory());
		} else if (hasPages && is != null && is.isSimilar(pageForward)) {
			navigateForward(p, e.getInventory());
		} else {
			if (hasPages && is != null) {
				for (int i = 0; i < inv.size(); i++) {
					if (is.isSimilar(getPageIndicator(i)))
						return;
				}
			} else
				onClick(e);
		}
	}

}
