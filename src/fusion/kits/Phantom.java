package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Phantom extends Kit {
	
	@Override
	public String getName() {
		return "Phantom";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.FEATHER);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.STONE_SWORD).name("&aPhantom Sword").lore("Danny's Ol' Dependable").build();
		ItemStack feather = new ItemBuilder(Material.FEATHER).name("&aPhantom Feather").lore("Time to take flight").build();

		return Arrays.asList(sword, feather);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemBuilder(Material.LEATHER_HELMET).color(Color.WHITE).build(), new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.WHITE).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.WHITE).build(), new ItemBuilder(Material.LEATHER_BOOTS).color(Color.WHITE).build() };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Take flight for 5 long seconds... don't fall!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 500.0;

	}

	@Command(name = "phantom", description = "Gives the Phantom kit.", usage = "/kit phantom", inGameOnly = true)
	public void kitPhantomCommand(CommandArgs args) {
		
		apply(args.getPlayer());

	}

}
