package fusion.kits;

import java.util.Collections;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;

/**
	 * 
	 * Created on Dec 5, 2016 by Jeremy Gooch.
	 * 
	 */

public class Turtle extends Kit {

	@Override
	public String getName() {
		return "Turtle";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.TURTLE_HELMET);
	}

	@Override
	public List<ItemStack> getItems() {
		
		ItemStack sword = new ItemBuilder(Material.STONE_SWORD).name("&aTurtle Sword").build();
		
		return Collections.singletonList(sword);
	}

	@Override
	public ItemStack[] getArmor() {
		
		ItemStack helmet = new ItemBuilder(Material.TURTLE_HELMET).build();
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.GREEN).build();
		ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS).color(Color.GREEN).build();
		
		return new ItemStack[] { helmet, chestplate, leggings, boots };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return new PotionEffect[] { new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0) };
	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Crouch to hide in your shell and", "drastically reduce damage!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 300.0;
	}

}
