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
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Stalker extends Kit {
	
	@Override
	public String getName() {
		return "Stalker";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.WITHER_SKELETON_SKULL);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return new PotionEffect[] { new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0) };
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aStalker Sword").lore("...").build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemBuilder(Material.LEATHER_HELMET).color(Color.BLACK).build(), new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.BLACK).build(),
				new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.BLACK).build(), new ItemBuilder(Material.LEATHER_BOOTS).color(Color.BLACK).build() };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Hide in the shadows for a buff to your strength and agility..." };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 500.0;

	}

	@Command(name = "stalker", description = "Gives the Stalker kit.", usage = "/kit stalker", inGameOnly = true)
	public void kitStalkerCommand(CommandArgs args) {
		
		apply(args.getPlayer());

	}

}
