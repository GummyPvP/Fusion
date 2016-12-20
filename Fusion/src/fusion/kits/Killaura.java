package fusion.kits;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class Killaura extends Kit {

	public String getName() {
		return "KillAura";
	}

	@Override
	public ItemStack getInventoryItem() {
		//return new ItemStack(Material.);
		return null;
	}

	@Override
	public List<ItemStack> getItems() {
		return null;
	}

	@Override
	public ItemStack[] getArmor() {
		return null;
	}


	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "e." };
	}


	@Override
	public boolean isDefault() {
		return false;
	}


	@Override
	public double getCost() {
		return 750.0;
	}
	@Command(name = "killaura", description = "Gives you the KillAura kit", usage = "/kit killaura", inGameOnly = true)
	public void kitHeavyCommand(CommandArgs args) {

		apply(args.getPlayer());

	}
	
	
}
