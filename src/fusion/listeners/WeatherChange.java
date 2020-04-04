package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import fusion.utils.Settings;

/**
	 * 
	 * Created on Apr 1, 2018 by Jeremy Gooch.
	 * 
	 */

public class WeatherChange implements Listener {
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		
		if (Settings.getSettings().WEATHER_ENABLED) return;
		
		e.getWorld().setThundering(false);
		
	}

}
