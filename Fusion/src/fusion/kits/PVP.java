package fusion.kits;

import java.util.Collections;
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
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class PVP extends Kit {

	@Override
	public String getName() {
		return "PVP";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.DIAMOND_SWORD);
	}

	@Override
	public PotionEffect[] getPotionEffects() {

		return null;

	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aPVP Sword").lore("u wot m8").build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE),
				new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return null;
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public double getCost() {
		return 0.0;

	}

	@Command(name = "pvp", description = "Gives the PVP kit.", usage = "/kit pvp", inGameOnly = true)
	public void kitPVPCommand(CommandArgs args) {
		
		apply(args.getPlayer());

	}

}
