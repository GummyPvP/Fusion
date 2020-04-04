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

public class Vigilante extends Kit {

	@Override
	public String getName() {
		return "Vigilante";
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
		
		ItemStack bow = new ItemBuilder(Material.BOW).name("&5Vigilante Longbow").lore("You're talented upclose or far but only with a bow!")
				.enchant(Enchantment.ARROW_INFINITE, 1).enchant(Enchantment.DAMAGE_ALL, 3).enchant(Enchantment.ARROW_DAMAGE, 3).build();
		
		ItemStack smoke = new ItemBuilder(Material.GUNPOWDER).name("&aSmoke").build();
		
		ItemStack arrow = new ItemBuilder(Material.ARROW).name("&aArrow").build();

		return Arrays.asList(bow, arrow, smoke);

	}

	@Override
	public ItemStack[] getArmor() {
		
		return new ItemStack[] { new ItemBuilder(Material.LEATHER_HELMET).color(Color.GREEN).build(), new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.GREEN).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.GREEN).build(), new ItemBuilder(Material.LEATHER_BOOTS).color(Color.GREEN).build() };
		
	}
	
	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Get a deadly longbow and a hideout!"};
		
	}

	@Override
	public boolean isDefault() {
		return false;
	}
	
	@Override
	public double getCost() {
		
		return 2500.0;
		
	}
	
	@Command(name = "Vigilante", description = "Gives the Vigilante kit.", usage = "/kit Vigilante", inGameOnly = true)
	public void kitVigilanteCommand(CommandArgs args) {

		apply(args.getPlayer());

	}

}
