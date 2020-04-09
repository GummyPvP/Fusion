package fusion.listeners;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import fusion.utils.ItemBuilder;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.ProtectedRegion.HealingItem;
import fusion.utils.editing.regions.Region;
import fusion.utils.editing.regions.RegionManager;
import fusion.utils.gui.KitGUI;
import fusion.utils.gui.ShopGUI;
import fusion.utils.gui.WarpGUI;
import fusion.utils.warps.WarpManager;

/**
 * 
 * Copyright GummyPvP. Created on May 20, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class PlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		mKitUser user = mKitUser.getInstance(player);

		if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CHEST
				&& !player.hasPermission("chests.view")) {

			e.setCancelled(true);

		}

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {

			if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.ENDER_CHEST) {

				if (player.getGameMode() == GameMode.CREATIVE) return;
				
				e.setCancelled(true);

				if (user.getCandies() >= 50) {

					Inventory healingGUI = Bukkit.createInventory(player, 54,
							ChatColor.RED + "Station: " + user.getHealingItem().toString());

					for (int i = 0; i < healingGUI.getSize(); i++) {

						healingGUI.addItem(user.getHealingItem().getItem().clone());

					}

					player.openInventory(healingGUI);

					player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(1, Tone.C));
					player.sendMessage(ChatColor.LIGHT_PURPLE + "You used a refill station which took " + ChatColor.RED
							+ "50 candies" + ChatColor.LIGHT_PURPLE + " from your balance");

					user.removeCandies(50);

					return;
				}

				Chat.getInstance().messagePlayer(player, "&cYou need 50 candies to use a healing station!");

				return;
			}

			if (item == null)
				return;

			switch (item.getType()) {
			case MUSHROOM_STEW:

				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Current Healing Item:")) {

					e.setCancelled(true);

					player.setItemInHand(new ItemBuilder(Material.POTION).name("&aCurrent Healing Item: &ePOTION").addPotionData(new PotionData(PotionType.INSTANT_HEAL)).build());

					player.updateInventory();

					mKitUser.getInstance(player).setHealingItem(HealingItem.POTION);

					player.playNote(player.getLocation(), Instrument.BASS_DRUM, Note.sharp(0, Tone.B));

					return;
				}

				if (player.getHealth() >= player.getMaxHealth())
					return;

				e.setCancelled(true);

				player.setHealth(player.getHealth() + 7 >= player.getMaxHealth() ? player.getMaxHealth()
						: player.getHealth() + 7);

				ItemStack bowl = new ItemBuilder(Material.BOWL).name("&bBowl").lore(
						Arrays.asList("A bowl is most useful when it's empty.", "Hah! Just kidding! You're gonna die."))
						.build();

				player.setItemInHand(bowl);

				player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);

				player.updateInventory();

				break;

			case POTION:

				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Current Healing Item:")) {
					
					e.setCancelled(true);

					player.setItemInHand(new ItemBuilder(Material.MUSHROOM_STEW).name("&aCurrent Healing Item: &eSOUP").build());

					player.updateInventory();

					mKitUser.getInstance(player).setHealingItem(HealingItem.SOUP);

					player.playNote(player.getLocation(), Instrument.SNARE_DRUM, Note.flat(0, Tone.B));

					return;
				}

				break;

			case NETHER_STAR:

				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Kit Selector")) {

					new KitGUI(player);

					player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.25F, 1);

				}

				break;

			case CHEST:

				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Cosmetic Selector")) {

					e.setCancelled(true);

					player.updateInventory();

					// new CostmeticGUI

				}

				break;

			case ENDER_CHEST:

				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Kit Shop")) {

					e.setCancelled(true);

					player.updateInventory();

					new ShopGUI(player);

					player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH, 0.5F, 1);

				}

				break;

			case COMPASS:

				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Warps")) {

					if (WarpManager.getInstance().getWarps().isEmpty()) {
						
						Chat.getInstance().messagePlayer(player, Chat.IMPORTANT_COLOR + "No warps are currently set!");
						
						return;
					}
					
					new WarpGUI(player);

					player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 1);

				}

				break;

			case LEATHER:

				if (player.hasPermission("region.view")) {

					if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
						return;

					if (RegionManager.getInstance().getRegions(e.getClickedBlock().getLocation().toVector())
							.isEmpty()) {

						Chat.getInstance().messagePlayer(player,
								Chat.STAFF_NOTIFICATION + "No regions are defined here!");

						return;
					}

					StringBuilder sb = new StringBuilder();

					for (Region region : RegionManager.getInstance()
							.getRegions(e.getClickedBlock().getLocation().toVector())) {

						sb.append(region.getName()).append(", ");

					}

					String regionList = sb.toString();

					Pattern pattern = Pattern.compile(", $");
					Matcher matcher = pattern.matcher(regionList);

					regionList = matcher.replaceAll("");

					Chat.getInstance().messagePlayer(player,
							Chat.STAFF_NOTIFICATION + "Regions defined here: " + Chat.SECONDARY_BASE + regionList);

				}

				break;

			default:

				if (mKitUser.getInstance(player).hasPreviousKit() && !mKitUser.getInstance(player).hasKit()) {

					if (item.getType() == Material.CLOCK) {

						mKitUser.getInstance(player).getPreviousKit().apply(player);

						player.updateInventory();

					}

				}

				break;
			}

		}
	}
}
