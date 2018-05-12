package org.mcwarfare.cubecore.koth.gameplay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mcwarfare.cubecore.Cubecore;
import org.mcwarfare.cubecore.koth.gameplay.managers.RegionManager;
import org.mcwarfare.cubecore.koth.utils.Day;
import org.mcwarfare.cubecore.koth.utils.region.Region;
import org.mcwarfare.cubecore.koth.utils.regions.KOTHRegion;
import org.mcwarfare.cubecore.koth.utils.regions.PointRegion;
import org.mcwarfare.cubecore.managers.StatsManager;

/**
 * 
 * Created on Jul 10, 2016 by Jeremy Gooch.
 * 
 */

public class Point {

	/**
	 * 
	 * The region instance to keep track of people
	 * 
	 */

	private PointRegion region;

	/**
	 * 
	 * The name of the KOTH
	 * 
	 */

	private String name;

	/**
	 * 
	 * Is the KOTH running?
	 * 
	 */

	private boolean active = false;

	/**
	 * 
	 * The player who is currently capturing the point
	 * 
	 */

	private Player capturingPlayer;

	/**
	 * 
	 * The player who captured the point, wins a point
	 * 
	 */

	private Player winner;

	/**
	 * 
	 * The time, in milliseconds, in which the KOTH began (used to shut it down
	 * after one hour of no activity)
	 * 
	 * 
	 */

	private long startTime = 0L;

	/**
	 * 
	 * The time, in milliseconds, in which the current capturing user began to
	 * capture the point (used to decide the winner of course)
	 * 
	 */

	private long capturingTime = 0L;

	/**
	 * 
	 * The time, in milliseconds, required to capture the point (we'll get this
	 * number from the minutes that the admin provides)
	 * 
	 */

	private int timeToCapture = 10; // While doing tests, this is about
									// 600k milliseconds, which is 10 minutes.

	/**
	 * 
	 * The day that the KOTH will automatically run
	 * 
	 */

	private Day day;

	/**
	 * 
	 * The hour (in 24 hour time) to automatically run the KOTH
	 * 
	 */

	private int hour;

	/**
	 * 
	 * The minutes (in 24 time) to automatically run the KOTH
	 * 
	 */
	
	private int minutes;

	public Point(String name, PointRegion region, int timeToCapture, Day day, int hour, int minutes) {

		this.region = region;
		this.name = name;
		this.timeToCapture = timeToCapture;
		this.day = day;
		this.hour = hour;
		this.minutes = minutes;

	}

	public static final String

			CAPPING = Cubecore.getInstance().getMessagesConfiguration().getString("messages.koth.capturing"),
			CAPTURED = Cubecore.getInstance().getMessagesConfiguration().getString("messages.koth.captured"),
			NOACTIVITY = Cubecore.getInstance().getMessagesConfiguration().getString("messages.koth.noactivity"),
			CAPPERCHANGE = Cubecore.getInstance().getMessagesConfiguration().getString("messages.koth.pointOwnerChange"),
			TIMENOTIFICATION = Cubecore.getInstance().getMessagesConfiguration().getString("messages.koth.timeNotification");

	public void tick() {
		
		if (!isActive()) {
			
			if (isTimeToStart()) {
				
				setActive(true);
				
			}
				
			return;
		}
		
		checkCapturer();
		
		tickCapture();
		
		// Update every minute to give an idea of how much time that user has left until they capture it
		
		if (formattedTimeCapturing() != null) {
			
			if (capturingPlayer == null) return;
			
			broadcastMessage(TIMENOTIFICATION.replace("{player}", capturingPlayer.getName()).replace("{koth}", region.getName()).replace("{time}", formattedTimeCapturing()));
			
		}
		
		// These messages should never appear, but if they do... we might as well be well aware of it.
		
		if (region == null) {
			
			broadcastMessage("The region for the KOTH does not exist, disabling KOTH. Please contact an administrator.");
			
			return;
		}
		
		if (region.getTracker() == null) {
			
			broadcastMessage("The tracker for the KOTH does not exist, disabling KOTH. Please contact an administrator.");
			
			return;
		}
		
		// Not tested, but this should disable the KOTH after one hour (if still active)
		
		if (hasHourPassed()) {
			
			broadcastMessage(NOACTIVITY.replace("{koth}", getName()));
			
			setActive(false);
			
		}

	}
	
	private boolean hasHourPassed() {
		
		if (startTime != 0L && TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - startTime) >= 1) return true;

		return false;
		
	}
	
	private boolean isTimeToStart() {
		
		if (Cubecore.getInstance().getToday() != getDay()) return false;
		
		if (Cubecore.getInstance().getHour() == getHour() && Cubecore.getInstance().getMinutes() == getMinutes()) return true;
		
		return false;
		
	}
	
	@SuppressWarnings("deprecation")
	private void checkCapturer() {
		
		// If no one is capturing, give ownership to next person in set.
		
		if (capturingPlayer == null) {
			
			if (region.getTracker().getPlayersOnPoint().isEmpty())
				return;
			
			beginCapture(Bukkit.getPlayer(region.getTracker().getPlayersOnPoint().iterator().next()));
			
		}
		
		// Assuming that they leave or teleport away, they won't be null, but they won't be in the set either, so we should handle this.
		
		if (capturingPlayer != null && !region.getTracker().getPlayersOnPoint().contains(capturingPlayer.getName())) {
			
			//broadcastMessage(capturingPlayer.getName() + " is no longer capturing KOTH!");
			broadcastMessage(CAPPERCHANGE.replace("{player}", capturingPlayer.getName()).replace("{koth}", region.getName()));
			
			capturingPlayer = null;
			
			for (Player on : Bukkit.getOnlinePlayers()) {
				
				StatsManager.getInstance().refreshScoreBoard(on, StatsManager.getInstance().hasCoolDown(on), getName());
				
			}
			
			return;
		}
		
	}

	@SuppressWarnings("deprecation")
	private void tickCapture() {
		
		if (capturingPlayer == null)
			return;
		
		if ((System.currentTimeMillis() - capturingTime) >= TimeUnit.MINUTES.toMillis(timeToCapture)) { // means they have been at the point for the required amount of time
			
			// they win
			winner = capturingPlayer;
			setActive(false);
			reset();
			
			broadcastMessage(CAPTURED.replace("{player}", winner.getName()).replace("{koth}", region.getName()));
			
			StatsManager.getInstance().addScore(getWinner());
			
			for (Player on : Bukkit.getOnlinePlayers()) {
				
				StatsManager.getInstance().refreshScoreBoard(on, StatsManager.getInstance().hasCoolDown(on), getName());
				
			}

		}

	}
	
	@SuppressWarnings("deprecation")
	private void beginCapture(Player player) {
		
		if (!active) return;
		
		if (player == null)
			return;
		
		capturingPlayer = player;
		capturingTime = System.currentTimeMillis();
		
		broadcastMessage(CAPPING.replace("{player}", this.capturingPlayer.getName()).replace("{koth}", getName()).replace("{time}", String.valueOf(10)));
		 
		for (Player on : Bukkit.getOnlinePlayers()) {
			
			StatsManager.getInstance().refreshScoreBoard(on, StatsManager.getInstance().hasCoolDown(on), getName());
			
		}

	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public boolean isActive() {
		return active;
	}

	@SuppressWarnings("deprecation")
	public void setActive(boolean active) {
		this.active = active;

		startTime = System.currentTimeMillis();
		
		String cords =  "&bX: "
				+ Points.get().getPoint(region.getName()).getRegion().getBounds().getCenterPoint().getX()
				+ " Y: "
				+ Points.get().getPoint(region.getName()).getRegion().getBounds().getCenterPoint().getBlockX()
				+ " Z: "
				+ Points.get().getPoint(region.getName()).getRegion().getBounds().getCenterPoint().getZ();
		
		broadcastMessage("&eThe " + region.getName() + " KOTH has " + (active ? "started @ coords " + cords : "ended") + "!");
		
		// Change scoreboard when we set it to active/inactive
		
		for (Player on : Bukkit.getOnlinePlayers()) {
			
			StatsManager.getInstance().refreshScoreBoard(on, StatsManager.getInstance().hasCoolDown(on), active ? getName() : null);
			
		}

	}

	public void reset() {

		this.capturingTime = 0L;
		this.capturingPlayer = null;
		this.startTime = 0L;

	}

	public Player getCapturingPlayer() {
		return capturingPlayer;
	}

	public void setCapturingPlayer(Player capturingPlayer) {
		this.capturingPlayer = capturingPlayer;
	}

	public String getName() {
		return name;
	}

	public PointRegion getRegion() {
		return region;
	}

	public Day getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	private void broadcastMessage(String string) {
		
		List<Region> regions = RegionManager.get().getRegions(this.getRegion().getBounds().getCenterPoint());
		
		if (!regions.isEmpty()) {
			
			for (Region region : regions) {
				
				if (!(region instanceof KOTHRegion)) continue;
				
				KOTHRegion kothRegion = (KOTHRegion) region;
				
				for (String playerNames : kothRegion.getTracker().getPlayersInRegion()) {
					
					Player player = Bukkit.getPlayer(playerNames);
					
					if (player == null) continue;
					
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bKOTH&7]&e " + string));
					
				}
				
			}
			
		} else Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bKOTH&7]&e " + string));

	}

	private String formattedTimeCapturing() {
		
		long elapsedTime = System.currentTimeMillis() - capturingTime;
		
		if ((TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + 1) % 60 != 0) return null;
		
		long neededTime = TimeUnit.MINUTES.toMillis(timeToCapture);
		
		long fixedTimeInMillis = neededTime - elapsedTime;
		
		if (fixedTimeInMillis == 0L) return null;
		
		Date date = new Date(fixedTimeInMillis);
		
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		
		return format.format(date);

	}

	public void save() {

		Cubecore.getInstance().getPointsFile().set("points." + name + ".region", region.getName());
		Cubecore.getInstance().getPointsFile().set("points." + name + ".timeToCapture", timeToCapture);
		Cubecore.getInstance().getPointsFile().set("points." + name + ".day", day.getName());
		Cubecore.getInstance().getPointsFile().set("points." + name + ".hour", hour);
		Cubecore.getInstance().getPointsFile().set("points." + name + ".minutes", minutes);

	}

}
