package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Spiderman extends Kit {

	@Override
	public String getName() {
		return "Spiderman";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.COBWEB);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aSpiderman Sword").lore("Spiderman 2 now on VHS!!!").build();
		ItemStack hairierBalls = new ItemBuilder(Material.SNOWBALL).name("&5Spiderman Webs").lore("Throw me to temporarily cover an area with webs!").amount(10).build();
		
		return Arrays.asList(sword, hairierBalls);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] {
				new ItemBuilder(Material.LEATHER_HELMET).color(Color.RED).build(),
				new ItemStack(Material.IRON_CHESTPLATE),
				new ItemStack(Material.IRON_LEGGINGS), 
				new ItemStack(Material.IRON_BOOTS) 
		};

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Throw webs at enemies to keep them at bay!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 500.0;

	}

	@Command(name = "spiderman", description = "Gives the Spiderman kit.", usage = "/kit spiderman", inGameOnly = true)
	public void kitSpidermanCommand(CommandArgs args) {
		
		apply(args.getPlayer());

	}
	
}
