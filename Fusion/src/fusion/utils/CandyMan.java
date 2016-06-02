package fusion.utils;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;

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
	}
	
	@Override
	public String getCustomName() {
		return "Candy Man";
	}
	
	@Override
	public void g(double d0, double d1, double d2) {
		return;
	}
	
	public static Object getPrivateField(String fieldName, Class<?> clazz, Object object) {
		
		Field field;
		Object o = null;
		
		for (Field fieldz : clazz.getDeclaredFields()) {
			
			Bukkit.broadcastMessage(fieldz.getName());
			
		}
		
		try {
			field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			o = field.get(object);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}
}
