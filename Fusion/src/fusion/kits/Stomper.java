package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Copyright GummyPvP. Created on May 20, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Stomper extends Kit {

	@Override
	public String getName() {
		return "Stomper";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.DIAMOND_BOOTS);
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack sword = new ItemBuilder(Material.STONE_SWORD).name("&aStomper Sword").lore(Arrays.asList("Squishy squishy")).enchant(Enchantment.DAMAGE_ALL, 1).build();
		
		return Arrays.asList(sword);
		
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS) };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}
	
	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Jump from the sky and squish your foes!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}
	
	@Override
	public double getCost() {
		return 750.0;
		
	}

	@Command(name = "stomper", description = "Gives the Stomper kit.", usage = "/kit stomper", inGameOnly=true)
	public void kitStomperCommand(CommandArgs args) {
		
		apply(args.getPlayer());
		
	}
	
}
