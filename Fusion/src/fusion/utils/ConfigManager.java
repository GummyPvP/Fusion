package fusion.utils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fusion.main.Main;

/**
 * 
 * Copyright GummyPvP. Created on Apr 12, 2016 All Rights Reserved.
 * 
 */

public class ConfigManager {

	private static ConfigManager configuration = new ConfigManager("config", false),
			spawn = new ConfigManager("spawn", false), warp = new ConfigManager("warps", false);

	public static ConfigManager getSpawnFile() {
		return spawn;
	}

	public static ConfigManager getConfig() {
		return configuration;
	}
	
	public static ConfigManager getWarpsFile() {
		return warp;
	}

	private File file;
	private FileConfiguration config;

	public ConfigManager(String fileName, boolean isPlayerFile) {

		if (!Main.getInstance().getDataFolder().exists()) {

			Main.getInstance().getDataFolder().mkdir();

		}

		if (!isPlayerFile) {
			
			file = new File(Main.getInstance().getDataFolder(), fileName + ".yml");

			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			config = YamlConfiguration.loadConfiguration(file);
			
		} else {
			
			file = new File(Main.getInstance().getDataFolder(), "/players/" + fileName + ".yml");

			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			config = YamlConfiguration.loadConfiguration(file);
			
		}

	}

	public void set(String path, Object value) {
		config.set(path, value);
		save();
	}

	public List<?> getList(String path) {

		return config.getList(path);

	}

	@SuppressWarnings("unchecked")
	public <T> T get(String path) {

		return (T) config.get(path);

	}

	public Set<String> getKeys() {
		return config.getKeys(false);
	}

	public Set<String> getKeys(boolean b) {
		return config.getKeys(b);
	}

	public List<String> getStringList(String path) {

		return config.getStringList(path);

	}

	public String getString(String path) {

		return config.getString(path);

	}

	public List<Map<?, ?>> getMapList(String path) {

		return config.getMapList(path);

	}

	public boolean contains(String path) {
		return config.contains(path);
	}

	public ConfigurationSection createSection(String path) {
		ConfigurationSection section = config.createSection(path);
		save();
		return section;
	}

	public ConfigurationSection getSection(String path) {

		ConfigurationSection section = config.getConfigurationSection(path);
		return section;

	}

	public void save() {
		try {
			config.save(file);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getBoolean(String string) {
		return config.getBoolean(string);
	}

	public Float getFloat(String string) {

		return ((float) config.getDouble(string));

	}

	public Double getDouble(String string) {

		return config.getDouble(string);

	}

	public int getInt(String string) {
		return config.getInt(string);
	}

	public void addDefault(String path, Object value) {

		config.addDefault(path, value);

		config.options().copyDefaults(true);

		save();

	}

	public void addDefaults(String defaults) {

		Main.getInstance().saveResource(defaults, false);

	}
	
	public static ConfigManager getPlayerFile(String player) {
		
		return new ConfigManager(player, true);
		
	}

	public ItemStack getItemStack(String string) {
		return config.getItemStack(string);
	}
}
