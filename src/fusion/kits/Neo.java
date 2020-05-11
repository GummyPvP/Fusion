package fusion.kits;

import java.util.Arrays;
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

public class Neo extends Kit {
	
	@Override
	public String getName() {
		return "Neo";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.ARROW);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aNeo Sword").lore(Arrays.asList("You take the blue pill—the story ends, you wake up in your bed and believe whatever you want to believe.", "You take the red pill—you stay in Wonderland, and I show you how deep the rabbit hole goes")).build();
		
		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.BLACK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Automatically shoot arrows back at a shooter!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 500.0;

	}

	@Command(name = "neo", description = "Gives the Neo kit.", usage = "/kit neo", inGameOnly = true)
	public void kitNeoCommand(CommandArgs args) {
		apply(args.getPlayer());
	}

}
