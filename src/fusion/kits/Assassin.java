package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;

/**
	 * 
	 * Created on Apr 2, 2018 by Jeremy Gooch.
	 * 
	 */

public class Assassin extends Kit {

	@Override
	public String getName() {
		return "Assassin";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.NETHER_STAR);
	}

	@Override
	public List<ItemStack> getItems() {
		ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aHidden Blade").lore("Was difficult to get through U.S. customs :(").build();
		ItemStack assassin = new ItemBuilder(Material.GLOWSTONE_DUST).name("&aClick to go invisible and assassinate someone").build();
		
		return Arrays.asList(sword, assassin);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Use your training to assassinate targets in a strengthed and cloaked state!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 1500.0;
	}

}
