package fusion.kits;

import java.util.Collections;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Hulk extends Kit {
	
	@Override
	public String getName() {
		return "Hulk";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.MINECART);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.STONE_SWORD).name("&aHulk Sword").lore("HULK SMASH!!!").build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { 
				new ItemBuilder(Material.LEATHER_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).color(Color.GREEN).build(),
				new ItemBuilder(Material.LEATHER_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).color(Color.GREEN).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).color(Color.GREEN).build(),
				new ItemBuilder(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).color(Color.GREEN).build() 
		};

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Pick players up and throw them to their deaths!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 750.0;

	}

	@Command(name = "hulk", description = "Gives the Hulk kit.", usage = "/kit hulk", inGameOnly = true)
	public void kitHulkCommand(CommandArgs args) {
		
		apply(args.getPlayer());

	}

}
