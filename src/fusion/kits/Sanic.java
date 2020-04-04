package fusion.kits;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

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

public class Sanic extends Kit {

	@Override
	public String getName() {
		return "Sanic";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new Potion(PotionType.SPEED).toItemStack(1);
	}

	@Override
	public PotionEffect[] getPotionEffects() {

		return new PotionEffect[] { new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2) };

	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aSanic Beating Stick")
				.lore("Hit em' with the sharp end.").enchant(Enchantment.DAMAGE_ALL, 2).build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemStack(Material.JACK_O_LANTERN), new ItemStack(Material.AIR),
				new ItemStack(Material.AIR), new ItemStack(Material.DIAMOND_BOOTS) };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Zoom around with speed 3!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {

		return 500.0;

	}

	@Command(name = "Sanic", description = "Gives the Sanic kit.", usage = "/kit Sanic", inGameOnly = true)
	public void kitPVPCommand(CommandArgs args) {
		apply(args.getPlayer());
	}

}
