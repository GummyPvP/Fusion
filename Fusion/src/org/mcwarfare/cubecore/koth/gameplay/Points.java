package org.mcwarfare.cubecore.koth.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.gameplay.managers.PointRegionManager;
import org.mcwarfare.cubecore.koth.utils.Day;
import org.mcwarfare.cubecore.koth.utils.region.Bounds;
import org.mcwarfare.cubecore.koth.utils.regions.PointRegion;

/**
	 * 
	 * Created on Jul 11, 2016 by Jeremy Gooch.
	 * 
	 */

public class Points {
	
	private Points() { }
	
	private static Points instance = new Points();
	
	public static Points get() {
		return instance;
	}
	
	private List<Point> points = new ArrayList<Point>();
	
	public List<Point> getPoints() {
		return points;
	}
	
	public void registerPoint(Point point) {
		
		this.points.add(point);
		
		point.getRegion().register();
	}
	
	public void deletePoint(Point point) {
		
		points.remove(point);
		point.getRegion().unregister();
		PointRegionManager.get().deleteRegion(point.getRegion());
		
	}
	
	public void loadPoint(String point) {
		
		if (!Cubecore.getInstance().getPointsFile().contains("points." + point)) {
			
			//Bukkit.broadcastMessage("loadPoint returned!");
			
			return;
		}
		
		//PointRegion region = PointRegionManager.get().getPointRegion(Cubecore.getInstance().getRegionsFile().getString("regions.points." + point + ".region"));
		
		World world = Bukkit.getWorld(Cubecore.getInstance().getRegionsFile().getString("regions.points." + point + ".world"));
		Vector min = Cubecore.getInstance().getRegionsFile().getVector("regions.points." + point + ".min");
		Vector max = Cubecore.getInstance().getRegionsFile().getVector("regions.points." + point + ".max");
		
		PointRegion region = new PointRegion(Cubecore.getInstance().getPointsFile().getString("points." + point + ".region"), new Bounds(world, min, max));
		
		Day day = Day.getByName(Cubecore.getInstance().getPointsFile().getString("points." + point + ".day"));
		int hour = Cubecore.getInstance().getPointsFile().getInt("points." + point + ".hour");
		int minutes = Cubecore.getInstance().getPointsFile().getInt("points." + point + ".minutes");
		
		Point loadedPoint = new Point(point, region, 10, day, hour, minutes);
		
		//Bukkit.broadcastMessage(loadedPoint.getRegion().getName() + ":" + Cubecore.getInstance().getPointsFile().getString("points." + point + ".region"));
		
		registerPoint(loadedPoint);
		
	}
	
	public void loadPoints() {
		
		if (Cubecore.getInstance().getPointsFile().contains("points") == false) return;
		
		for (String section : Cubecore.getInstance().getPointsFile().getSection("points").getKeys(false)) {
			
			loadPoint(section);
			
			//Bukkit.broadcastMessage(section);
			
		}
		
	}
	
	public void savePoints() {
		
		for (Point point : points) {
			
			point.getRegion().save();
			point.save();
			
		}
	}
	
	public Point getPoint(String name) { // we will base this off of it's region's name
		
		for (Point point : points) {
			
			if (point.getName().equalsIgnoreCase(name)) return point;
			
		}
		
		return null;
		
	}
	
	public void start() {
		
		KOTHLoop.get().start();
		
		KOTHLoop.get().run();
		
	}

}
