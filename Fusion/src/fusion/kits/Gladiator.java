package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
 * 
 * Copyright GummyPvP. Created on May 15, 2016 by Gummy Dev Team. All Rights
 * Reserved.
 * 
 */

public class Gladiator extends Kit {

	@Override
	public String getName() {
		return "Gladiator";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.IRON_FENCE);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aForged Longsword").lore("One will survive.").build();
		ItemStack bars = new ItemBuilder(Material.IRON_FENCE).name("&aClick someone to start a 1v1").build();
		
		return Arrays.asList(sword, bars);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE),
				new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Teleport someone into a box for a fair 1v1!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {

		return 750.0;

	}

	@Command(name = "Gladiator", description = "Gives the Gladiator kit.", usage = "/kit Gladiator", inGameOnly = true)
	public void kitGladiatorCommand(CommandArgs args) {

		apply(args.getPlayer());

	}

}
