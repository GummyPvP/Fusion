package fusion.kits;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Anchor extends Kit {
	

	@Override
	public String getName() {
		return "Anchor";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.ANVIL);
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&aAnchor Sword").lore("bruh moment").build();

		return Collections.singletonList(sword);

	}

	@Override
	public ItemStack[] getArmor() {

		return new ItemStack[] { new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.IRON_CHESTPLATE),
				new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS) };

	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "Take/give no knockback" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 500.0;

	}

	@Command(name = "anchor", description = "Gives the Anchor kit.", usage = "/kit anchor", inGameOnly = true)
	public void kitAnchorCommand(CommandArgs args) {
		
		apply(args.getPlayer());

	}


}
