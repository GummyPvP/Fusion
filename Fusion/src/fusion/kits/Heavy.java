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
 * Copyright GummyPvP. Created on May 21, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class Heavy extends Kit {

	public String getName() {
		return "Heavy";
	}

	public ItemStack getInventoryItem() {
		return new ItemStack(Material.ANVIL);
	}

	public List<ItemStack> getItems() {
		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aHeavy Sword").lore("Bash 'em in!")
				.enchant(Enchantment.DAMAGE_ALL, 1).build();
		ItemStack axe = new ItemBuilder(Material.IRON_AXE).name("&aHeavy Axe").lore("Bash 'em in!")
				.enchant(Enchantment.DAMAGE_ALL, 2).enchant(Enchantment.KNOCKBACK, 1).build();

		return Arrays.asList(sword, axe);
	}

	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.DIAMOND_CHESTPLATE),
				new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };
	}

	public PotionEffect[] getPotionEffects() {
		return new PotionEffect[] { new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3) };
	}

	public String[] getSpecialAdvantageStrings() {
		return null;

	}

	public boolean isDefault() {
		return true;
	}

	public double getCost() {

		return 0.0;

	}

	@Command(name = "heavy", description = "Gives you the Heavy kit", usage = "/kit heavy", inGameOnly = true)
	public void kitHeavyCommand(CommandArgs args) {

		apply(args.getPlayer());

	}

}
