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
	 * Created on Dec 17, 2016 by Jeremy Gooch.
	 * 
	 */

public class Kangaroo extends Kit {

	@Override
	public String getName() {
		return "Kangaroo";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.FIREWORK);
	}

	@Override
	public List<ItemStack> getItems() {
		ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aKangaroo Sword").lore("Boing!").build();
		ItemStack firework = new ItemBuilder(Material.FIREWORK).name("&aKangaroo Gadget").lore("Right click to hop!").build();
		
		return Arrays.asList(sword, firework);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Jump higher and farther using your kangaroo gadget!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 250.0;
	}
	
	@Command(name = "Kangaroo", description = "Gives the Kangaroo kit.", usage = "/kit Kangaroo", inGameOnly = true)
	public void kitKangarooCommand(CommandArgs args) {
		apply(args.getPlayer());
	}

}
