package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fusion.utils.Chat;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import klap.utils.mPlayer;

/**
	 * 
	 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Archer extends Kit {

		@Override
		public String getName() {
			return "Archer";
		}

		@Override
		public List<ItemStack> getItems() {
			
			ItemStack sword = new ItemBuilder(Material.STONE_SWORD).name("&aArcher Sword").lore(Arrays.asList("This sword has an EXTREMELY dull blade.", "It'll do little damage, so stick to your bow!")).build();
			ItemStack bow = new ItemBuilder(Material.BOW).name("&aArcher Bow").lore("Use this bow at a distance!").enchant(Enchantment.ARROW_INFINITE, 1).enchant(Enchantment.ARROW_KNOCKBACK, 2).build();
			
			return Arrays.asList(sword, bow, new ItemStack(Material.ARROW));
			
		}
		
		@Override
		public List<ItemStack> getArmor() {
			
			return Arrays.asList(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS));
			
		}
		
		@Override
		public boolean isDefault() {
			return true;
		}
		
		@Command(name = "archer", description = "Gives the Archer kit.", usage = "/kit archer", inGameOnly=true)
		public void kitPVPCommand(CommandArgs args) {
			
			mPlayer user = mPlayer.getInstance(args.getPlayer());
			
			if (user.hasKit()) {
				
				Chat.getInstance().messagePlayer(args.getPlayer(), Chat.ALREADY_USED_KIT);
				
				return;
			}
			
			apply(args.getPlayer());
			
		}

}
