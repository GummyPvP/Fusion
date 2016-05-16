package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class PVP extends Kit {

		@Override
		public String getName() {
			return "PVP";
		}

		@Override
		public List<ItemStack> getItems() {
			
			ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aPVP Sword").lore("u wot m8").enchant(Enchantment.DAMAGE_ALL, 1).build();
			
			return Arrays.asList(sword);
			
		}
		
		@Override
		public List<ItemStack> getArmor() {
			
			return Arrays.asList(new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS));
			
		}
		
		@Override
		public boolean isDefault() {
			return true;
		}
		
		@Command(name = "pvp", description = "Gives the PVP kit.", usage = "/kit pvp", inGameOnly=true)
		public void kitPVPCommand(CommandArgs args) {
			
			//KitManager.getInstance().setKit(mPlayer.getInstance(args.getPlayer()), this);
			
			apply(args.getPlayer());
			
		}

}
