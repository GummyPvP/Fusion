package fusion.kits.utils;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;

import fusion.main.Fusion;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.regions.ProtectedRegion;
import fusion.utils.editing.regions.ProtectedRegion.HealingItem;
import fusion.utils.editing.regions.Region;
import fusion.utils.editing.regions.RegionManager;

/**
 * 
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public abstract class Kit {

	/**
	 * 
	 * @return name of kit
	 */
	public abstract String getName();

	/**
	 * 
	 * @return item for KitGUI
	 */
	public abstract ItemStack getInventoryItem();

	/**
	 * 
	 * @return ItemStack list of items
	 */
	public abstract List<ItemStack> getItems();

	/**
	 * 
	 * @return ItemStack list of armor. Make sure to put the armor in the
	 *         inverse order when adding, boots, leggings, chestplate, helmet
	 */
	public abstract ItemStack[] getArmor();

	/**
	 * 
	 * @return Potion effects that should be applied.
	 */
	public abstract PotionEffect[] getPotionEffects();

	public abstract String[] getSpecialAdvantageStrings();

	/**
	 * 
	 * @return if this kit is unlocked by purchasing in shop or is free by
	 *         default.
	 */
	public abstract boolean isDefault();

	public abstract double getCost();
	
	public void apply(Player player) {

		mKitUser user = mKitUser.getInstance(player);

		Region region = RegionManager.getInstance()
				.getSmallestRegion(RegionManager.getInstance().getRegions(player.getLocation().toVector()));
		
		if (user.hasKit()) {

			Chat.getInstance().messagePlayer(player,
					String.format(Chat.BASE_COLOR + "You already have kit " + Chat.IMPORTANT_COLOR + "%s"
							+ Chat.BASE_COLOR + "! Please type /clearkit to choose another kit.",
							user.getKit().getName()));

			return;
		}
		
		if (Fusion.getInstance().getEventModeHandler().isInEventMode()) {
			
			if (Fusion.getInstance().getEventModeHandler().getAllowedKits().contains(this)) {
				giveItems(player, region, user); // allow this player to use the kit regardless of ownership or local region blockage
				return;
			}
			
			Chat.getInstance().messagePlayer(player, Chat.BASE_COLOR + getName() + " is currently blocked globally due to an event!");
			
			return;
		}
		
		if (region != null && region instanceof ProtectedRegion) {

			if (((ProtectedRegion) region).getBlockedKits().contains(this)) {

				Chat.getInstance().messagePlayer(player,
                        Chat.BASE_COLOR + getName() + " is blocked in this area!");
				return;
			}

		}

		if (!user.ownsKit(this) && !isDefault()) {

			Chat.getInstance().messagePlayer(player,
					String.format(Chat.BASE_COLOR + "You do not own " + Chat.IMPORTANT_COLOR + "%s", getName()));

			return;
		}
		
		giveItems(player, region, user);

	}
	
	private void giveItems(Player player, Region region, mKitUser user) {
		
		user.setKit(this);
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);

		for (PotionEffect effects : player.getActivePotionEffects()) {

			player.removePotionEffect(effects.getType());

		}

		Chat.getInstance().messagePlayer(player, "&aYou've recieved kit " + Chat.IMPORTANT_COLOR + getName() + ".");

		for (ItemStack items : getItems()) {

			player.getInventory().addItem(items);

		}

		
		player.getInventory().setHelmet(getArmor()[0]);
		player.getInventory().setChestplate(getArmor()[1]);
		player.getInventory().setLeggings(getArmor()[2]);
		player.getInventory().setBoots(getArmor()[3]);

		if (getPotionEffects() != null) {

			for (PotionEffect effect : getPotionEffects()) {

				if (effect == null)
					break;

				player.addPotionEffect(effect);

			}

		}

		if (region != null && region instanceof ProtectedRegion) {

			ProtectedRegion protectedRegion = (ProtectedRegion) region;
			
			int amount = (protectedRegion.areRefillsAllowed() ? player.getInventory().getSize() : 8);
			
			if (protectedRegion.getHealingItem() == HealingItem.ANY) {
				
				for (int i = 0; i < amount; i++) {
					
					player.getInventory().addItem(user.getHealingItem().getItem());
					
				}
				
			} else {
				
				for (int i = 0; i < amount; i++) {
					
					player.getInventory().addItem(protectedRegion.getHealingItem().getItem());
					
				}
				
			}
		} else {
			
			for (int i = 0; i < player.getInventory().getSize(); i++) {
				
				player.getInventory().addItem(user.getHealingItem().getItem());
				
			}
			
		}


		player.setMetadata("noFall", new FixedMetadataValue(Fusion.getInstance(), true));

		player.playSound(player.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1, 1);

		player.updateInventory();
	}

}
