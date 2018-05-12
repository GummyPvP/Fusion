package org.mcwarfare.cubecore.koth.utils;

	/**
	 * 
	 * Created on Jul 13, 2016 by Jeremy Gooch.
	 * 
	 */

public enum Day {
	
	SUNDAY ("Sunday", 1), MONDAY ("Monday", 2), TUESDAY ("Tuesday", 3), WEDNESDAY ("Wednesday", 4), THURSDAY ("Thursday", 5), FRIDAY ("Friday", 6), SATURDAY ("Saturday", 7);
	
	private String name;
	private int day;
	
	Day(String name, int day) {
		
		this.name = name;
		this.day = day;
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getDayInteger() {
		return day;
	}
	
	public static Day getByName(String name) {
		
		for (Day day : values()) {
			
			if (day.getName().equalsIgnoreCase(name)) return day;
			
		}
		
		return null;
		
	}
	
	public static Day getByInteger(int dayInt) {
		
		for (Day day : values()) {
			
			if (day.getDayInteger() == dayInt) return day;
			
		}
		
		return null;
		
	}
}
