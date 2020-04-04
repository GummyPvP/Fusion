package fusion.kits;

import java.util.Collections;
import java.util.List;

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

public class Wimp extends Kit {

	@Override
	public String getName() {
		return "Wimp";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.LILY_PAD);
	}

	@Override
	public PotionEffect[] getPotionEffects() {

		return null;

	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.LILY_PAD).name("&1&lBoney Fist")
				.unsafeEnchant(Enchantment.DAMAGE_ALL, 7).lore("Don't break a bone ;)").build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemBuilder(Material.HAY_BLOCK).build(),
				new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).build(),
				new ItemBuilder(Material.CHAINMAIL_LEGGINGS).build(),
				new ItemBuilder(Material.CHAINMAIL_BOOTS).build() };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Gets stronger after each kill and faster when almost dead!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {

		return 1000.0;

	}

	@Command(name = "Wimp", description = "Gives the Wimp kit.", usage = "/kit Wimp", inGameOnly = true)
	public void kitWimpCommand(CommandArgs args) {

		apply(args.getPlayer());

	}

}
