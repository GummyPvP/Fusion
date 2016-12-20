package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
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
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Leonidas extends Kit {

	@Override
	public String getName() {
		return "Leonidas";
	}
	
	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.ARROW);
	}
	
	@Override
	public PotionEffect[] getPotionEffects() {
		
		return new PotionEffect[] { new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)};
		
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack bow = new ItemBuilder(Material.STONE_SWORD).name("&aLeonida's Spear ").lore("&cYou're agile and poke with ease.")
				.enchant(Enchantment.DAMAGE_ALL, 1).enchant(Enchantment.DURABILITY, 3).build();
		
		ItemStack kiss = new ItemBuilder(Material.GOLDEN_APPLE).name("&aGoddess's Kiss").lore("&9Eat to gain the blessing of the gods.").build();

		return Arrays.asList(bow, kiss);

	}

	@Override
	public ItemStack[] getArmor() {
		
		return new ItemStack[] { new ItemBuilder(Material.LEATHER_HELMET).color(Color.FUCHSIA).build(), new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.FUCHSIA).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.FUCHSIA ).build(), new ItemBuilder(Material.CHAINMAIL_BOOTS).enchant(Enchantment.PROTECTION_FALL, 0).build() };
		
	}
	
	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Recieve a blessing from the gods and fight through all."};
		
	}

	@Override
	public boolean isDefault() {
		return false;
	}
	
	@Override
	public double getCost() {
		
		return 1750.0;
		
	}
	
	@Command(name = "Leonidas", description = "Gives the Leonidas kit.", usage = "/kit Leonidas", inGameOnly = true)
	public void kitLeonidasCommand(CommandArgs args) {

		apply(args.getPlayer());

	}

}
