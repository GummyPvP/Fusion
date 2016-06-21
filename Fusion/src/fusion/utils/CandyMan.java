package fusion.utils;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_7_R4.EntityVillager;
import net.minecraft.server.v1_7_R4.World;

/**
 * 
 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class CandyMan extends EntityVillager {

	public CandyMan(World world) {
		super(world);
		
		setCustomNameVisible(true);
		
		setProfession(2);
		
		setCustomName(ChatColor.GREEN + "Charlie The Candy Man");
		
		((Villager) getBukkitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1, true));
		
		//setEquipment(3, CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_CHESTPLATE)));
	}
	
	@Override
	public void makeSound(String s, float f, float f1) {
		return;
	}
	
	@Override
	public void e(float arg0, float arg1) {
		return;
	}
	
	@Override
	public boolean isInvulnerable() {
		return true;
	}
	
	public static Object getPrivateField(String fieldName, Class<?> clazz, Object object) {
		
		Field field;
		Object o = null;
		
		try {
			field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			o = field.get(object);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}
	

}
