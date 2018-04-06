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
	 * Created on Apr 6, 2018 by Jeremy Gooch.
	 * 
	 */

public class Monk extends Kit {
	
	@Override
	public String getName() {
		return "Monk";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.BLAZE_ROD);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aMonk Sword").lore("Violence as a way of achieving racial justice is both impractical and immoral... but oh well!").build();
		ItemStack rod = new ItemBuilder(Material.BLAZE_ROD).name("&aClick on someone to disarm them!").build();
		
		return Arrays.asList(sword, rod);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE),
				new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Switch the item your enemy is holding with a random item in their inventory to effectively disarm them!" };
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public double getCost() {
		return 0.0;
	}

	@Command(name = "Monk", description = "Gives the Monk kit.", usage = "/kit Monk", inGameOnly = true)
	public void kitMonkCommand(CommandArgs args) {
		apply(args.getPlayer());
	}

}
