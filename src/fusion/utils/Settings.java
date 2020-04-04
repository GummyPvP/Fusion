package fusion.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import fusion.main.Fusion;

/**
	 * 
	 * Created on Jul 10, 2017 by Jeremy Gooch.
	 * 
	 */

public class Settings {
	
	private Settings() { }
	
	private static Settings instance = new Settings();
	
	public static Settings getSettings() {
		return instance;
	}
	
	public String SERVER_NAME;
	public String KIT_GUI_NAME;
	public String SHOP_GUI_NAME;
	public String WARP_GUI_NAME;
	
	public boolean WEATHER_ENABLED;
	
	public void initSettings() {
		
		SERVER_NAME = initSetting("SERVER_NAME");
		KIT_GUI_NAME = initSetting("KIT_GUI_NAME");
		SHOP_GUI_NAME = initSetting("SHOP_GUI_NAME");
		WARP_GUI_NAME = initSetting("WARP_GUI_NAME");
		WEATHER_ENABLED = initSetting("WEATHER_ENABLED");
		
	}
	
	private static <T> T initSetting(String path) {
		
		if (!Fusion.getInstance().getConfiguration().contains(path)) { // check the config file directly from the jar in case there were any updates
			
			InputStream tempStream = Fusion.getInstance().getResource("config.yml");
			
			File tempFile = new File("");
			
			try {
				FileUtils.copyInputStreamToFile(tempStream, tempFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			YamlConfiguration tempConfig = YamlConfiguration.loadConfiguration(tempFile);
			
			if (!tempConfig.contains(path)) {
				throw new NullPointerException("Could not find the path specified");
			}
			
			Fusion.getInstance().getConfiguration().set(path, tempConfig.get(path));
			
			initSetting(path);
			
			try {
				tempStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return Fusion.getInstance().getConfiguration().get(path);
		
	}

}
