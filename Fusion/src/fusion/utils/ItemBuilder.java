package fusion.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * 
 * Easy item manipulation by using the Builder format.
 * <br><br>
 * <code>ItemStack item = new ItemBuilder(Material.LEATHER_HELMET).color(Color.RED).name("&cShrek").build();</code>
 * 
 * @author Jeremy Gooch
 *
 */

public class ItemBuilder {
	
	private ItemStack item;
	
	/**
	 * 
	 * Constructs a new ItemBuilder object.
	 * 
	 * @param is - ItemStack
	 */
	
	public ItemBuilder(ItemStack is) {
		
		item = is;
		
	}
	
	/**
	 * 
	 * Constructs a new ItemBuilder object.
	 * 
	 * @param mat - Material
	 */
	
	public ItemBuilder(Material mat) {
		
		item = new ItemStack(mat);
		
	}
	
	/**
	 * 
	 * Sets the display name of the ItemStack.
	 * 
	 * @param name - String - name to set object. Supporting color codes with '&' character.
	 * @return current instance of ItemBuilder object.
	 */
	
	public ItemBuilder name(String name) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(im);
		return this;
	}
	
	/**
	 * 
	 * Adds a line of text to the ItemStack's lore. If ItemStack has current lore, the text will automatically be added to the next line.
	 * 
	 * @param lore - String - text to add to the ItemStack's lore.
	 * @return current instance of ItemBuilder object.
	 */
	
	public ItemBuilder lore(String lore) {
		List<String> itemLore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<String>();
		
		itemLore.add(lore);
		
		lore(itemLore);
		return this;
	}
	
	/**
	 * 
	 * Set the lore of an ItemStack.
	 * 
	 * @param lore - List(String) - lines of text to add to the ItemStack's lore.
	 * @return current instance of ItemBuilder object.
	 */
	
	public ItemBuilder lore(List<String> lore) {
		ItemMeta im = item.getItemMeta();
		
		im.setLore(lore);
		
		item.setItemMeta(im);
		return this;
	}
	
	/**
	 * 
	 * Clear's the lore of an ItemStack.
	 * 
	 * @return current instance of ItemBuilder
	 */
	
	public ItemBuilder clearLore() {
		
		List<String> emptyList = new ArrayList<String>();
		
		lore(emptyList);
		
		return this;
	}
	
	/**
	 * 
	 * Clear the enchantments of an ItemStack, if it has any.
	 * 
	 * @return current instance of ItemBuilder
	 */
	
	public ItemBuilder clearEnchantments() {
		
		if (item.getItemMeta().hasEnchants()) {
			
			for (Enchantment enchantments : item.getEnchantments().keySet()) {
				item.removeEnchantment(enchantments);
			}
			
		}
		return this;
	}
	
	/**
	 * 
	 * Clears a specified enchantment, if the ItemStack contains it.
	 * 
	 * @param enchant - Enchantment - the enchantment to remove from the ItemStack.
	 * @return current instance of ItemBuilder
	 */
	
	public ItemBuilder clearEnchantment(Enchantment enchant) {
		
		if (item.getItemMeta().hasEnchants()) {
			
			if (item.getEnchantments().containsKey(enchant)) {
				
				ItemMeta im = item.getItemMeta();
				
				im.removeEnchant(enchant);
				item.setItemMeta(im);
				
			}
			
		}
		
		return this;
	}
	
	/**
	 * 
	 * Enchants the item with the given enchantment and level.
	 * 
	 * @param enchantment - Enchantment to enchant with.
	 * @param level - Level of enchantment.
	 * @return current instance of ItemBuilder
	 */
	public ItemBuilder enchant(Enchantment enchantment, int level) {
		
		item.addEnchantment(enchantment, level);
		
		return this;
		
	}
	
	/**
	 * 
	 * Sets the amount of the ItemStack.
	 * 
	 * @param amount - Integer - the amount to set the ItemStack to.
	 * @return current instance of ItemBuilder
	 */
	
	public ItemBuilder amount(int amount) {
		
		item.setAmount(amount > 0 ? amount : 1);
		
		return this;
	}
	
	/**
	 * 
	 * Sets the durability of an ItemStack.
	 * 
	 * @param durability - Integer - the durability to set the ItemStack to.
	 * @return current instance of ItemBuilder
	 */
	
	public ItemBuilder durability(int durability) {
		
		item.setDurability((short) durability);
		
		return this;
	}
	
	/**
	 * 
	 * Sets the color of leather armor, if the ItemStack is leather armor.
	 * 
	 * @param color - Color - the color to set the leather armor to.
	 * @return current instance of ItemBuilder
	 */
	
	public ItemBuilder color(Color color) {
		
		if (item.getType().toString().contains("LEATHER_")) {
			
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			
			meta.setColor(color);
			
			item.setItemMeta(meta);
		}
		
		return this;
	}
	
	/**
	 * 
	 * Method to set an ItemStack's data.
	 * 
	 * @param data - Integer - the data to set the data of the ItemStack.
	 * @return current instance of ItemBuilder
	 */
	
	@Deprecated
	public ItemBuilder data(int data) {
		
		item.getData().setData((byte) data);
		
		return this;
	}
	
	/**
	 * 
	 * Build the object, to get it in the newest form.
	 * 
	 * @return ItemStack
	 */
	
	public ItemStack build() {
		return item;
	}

}