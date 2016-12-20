package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Viper extends Kit {

	@Override
	public String getName() {
		return "Viper";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new Potion(PotionType.POISON).toItemStack(1);
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aViper Sword").lore(Arrays.asList("Ricin is covering the", "blade of this sword!")).build();
		
		return Arrays.asList(sword);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.AIR), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)};
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}
	
	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "1/3 chance of poisoning someone with every hit!" };
	}

	@Override
	public boolean isDefault() {
		return true;
	}
	
	@Override
	public double getCost() {
		return 0.0;
		
	}
	
	@Command(name = "viper", description = "Gives you the Viper kit", usage = "/kit viper", inGameOnly = true)
	public void kitViperCommand(CommandArgs args) {
		
		apply(args.getPlayer());
		
	}
	
}
