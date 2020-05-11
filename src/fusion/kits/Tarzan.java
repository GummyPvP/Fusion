package fusion.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Tarzan extends Kit {
	
	@Override
	public String getName() {
		return "Tarzan";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.VINE);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.STONE_SWORD).name("&aTarzan Sword").lore("Finna clap some gorilla cheeks ( ͡° ͜ʖ ͡°)").enchant(Enchantment.DAMAGE_ALL, 1).build();
		ItemStack vines = new ItemBuilder(Material.VINE).name("&5Tarzan Vines").lore("Hold these next to surfaces to climb them").enchant(Enchantment.DURABILITY, 0).build();
		
		return Arrays.asList(sword, vines);

	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };
	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Climb walls by summoning vines on surfaces!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 300.0;

	}

	@Command(name = "tarzan", description = "Gives the Tarzan kit.", usage = "/kit tarzan", inGameOnly = true)
	public void kitTarzanCommand(CommandArgs args) {
		apply(args.getPlayer());
	}

}
