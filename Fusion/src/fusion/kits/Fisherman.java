package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

public class Fisherman extends Kit {

	@Override
	public String getName() {
		return "Fisherman";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.FISHING_ROD);
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aFisherman Sword").lore(Arrays.asList("Teach a man to fish, and", "he'll wreck some fools.")).build();
		ItemStack rod = new ItemBuilder(Material.FISHING_ROD).name("&5Fisherman Rod").lore("Use this to reel people in!").unsafeEnchant(Enchantment.DURABILITY, 10).build();
		
		return Arrays.asList(sword, rod);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS) };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return new PotionEffect[] { new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0) };
	}
	
	@Override
	public String[] getSpecialAdvantageStrings() {
		
		return new String[] { "Get a magical teleporting fishing rod!" };
		
	}

	@Override
	public boolean isDefault() {
		return true;
	}
	
	@Override
	public double getCost() {
		
		return 0.0;
		
	}
	
	@Command(name = "fisherman", description = "Gives the Fisherman kit.", usage = "/kit fisherman", inGameOnly=true)
	public void kitFishermanCommand(CommandArgs args) {
		
		apply(args.getPlayer());
		
	}

}
