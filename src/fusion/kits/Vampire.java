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

/**
 * 
 * Copyright GummyPvP. Created on May 15, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Vampire extends Kit {

	@Override
	public String getName() {
		return "Vampire";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.ROSE_BUSH);
	}

	@Override
	public PotionEffect[] getPotionEffects() {

		return null;

	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.GHAST_TEAR).name("&cBloody Tear")
				.lore("Why ask for blood when you can take it?").unsafeEnchant(Enchantment.DAMAGE_ALL, 4).build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] {
				new ItemBuilder(Material.LEATHER_HELMET).color(Color.RED)
						.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				new ItemBuilder(Material.LEATHER_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.color(Color.RED).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.color(Color.RED).build(),
				new ItemBuilder(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.color(Color.RED).build() };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Drains the targets blood with ease." };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 2000.0;

	}

	@Command(name = "Vampire", description = "Gives the Vampire kit.", usage = "/kit Vampire", inGameOnly = true)
	public void kitVampireCommand(CommandArgs args) {

		apply(args.getPlayer());

	}

}
