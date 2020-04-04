package fusion.kits;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.Kit;
import fusion.utils.ItemBuilder;

public class SpellCaster extends Kit {

	@Override
	public String getName() {
		return "SpellCaster";
	}

	@Override
	public ItemStack getInventoryItem() {
		return new ItemStack(Material.STICK);
	}

	@Override
	public List<ItemStack> getItems() {

		ItemStack sword = new ItemBuilder(Material.IRON_SWORD).name("&1&lCAST SPELLS UPON 'EM!").unsafeEnchant(Enchantment.DURABILITY, 1).lore("GIVE 'EM HELL!").build();
		
		return Collections.singletonList(sword);
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] { new ItemBuilder(Material.CHAINMAIL_HELMET).build(),
				new ItemBuilder(Material.IRON_CHESTPLATE).build(),
				new ItemBuilder(Material.IRON_LEGGINGS).build(),
				new ItemBuilder(Material.IRON_BOOTS).build() };
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public String[] getSpecialAdvantageStrings() {
		return new String[] { "YOU'RE A WIZARD HARRY!" };
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public double getCost() {
		return 2500.0;
	}

}
