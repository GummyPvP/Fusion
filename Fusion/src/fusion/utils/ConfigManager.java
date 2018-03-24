package fusion.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fusion.main.Fusion;


/**
 * 
 * Created on Apr 30, 2017 by Jeremy Gooch.
 * 
 */

public class ConfigManager {

	private File file;
	private FileConfiguration config;

	public ConfigManager(String fileName, String dir) {

		if (!Fusion.getInstance().getDataFolder().exists()) {

			Fusion.getInstance().getDataFolder().mkdir();

		}

		if (dir != null && !dir.isEmpty()) {
			File dirFolder = new File("plugins" + File.separator + "Fusion" + File.separator + dir);
			if (!dirFolder.exists()) {
				dirFolder.mkdir();
			}
		}

		file = new File(Fusion.getInstance().getDataFolder(),
				(dir == null ? fileName + ".yml" : File.separator + dir + File.separator + fileName + ".yml"));

		if (!file.exists()) {
			try {
				if (dir != null) {
					file.createNewFile();
					return;
				} 
				
				attemptFileGrabFromJar(fileName, false);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		config = YamlConfiguration.loadConfiguration(file);

	}
	
	public ConfigManager(File file) {
		
		if (!Fusion.getInstance().getDataFolder().exists()) {

			Fusion.getInstance().getDataFolder().mkdir();

		}
		
		config = YamlConfiguration.loadConfiguration(file);
		
	}
	
	public void setFile(File file) {
		this.file = file;
		config = YamlConfiguration.loadConfiguration(this.file);
	}
	
	public File getFile() {
		return file;
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

		Fusion.getInstance().saveResource(defaults, false);

	}

	public ItemStack getItemStack(String string) {
		return config.getItemStack(string);
	}

	public Vector getVector(String string) {
		return config.getVector(string);
	}

	public Long getLong(String s) {
		return config.getLong(s);
	}
	
	public static void attemptFileGrabFromJar(String fileName, boolean replace) {
		try {
			
			Fusion.getInstance().saveResource(fileName + ".yml", replace);
			
		} catch (Exception e) { }
		
	}
	
	public static File attemptFileGrabFromJar(String path, String fileName) { // probably not useful anymore
		
		File file = new File(path + File.pathSeparator + fileName + ".yml"); // create file where we want it
		
		InputStream stream = Fusion.getInstance().getResource(fileName + ".yml"); // get file data from jar
		
		try {
			FileUtils.copyInputStreamToFile(stream, file); // copy data into file using apache commons (thanks stackoverflow for spreading this knowledge)
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return file;
	}
	
	public static File transferData(ConfigManager copying, ConfigManager copyTo) throws IOException {
		
		InputStream newData = new FileInputStream(copying.file);
		
		FileUtils.copyInputStreamToFile(newData, copyTo.file);
		
		return copyTo.file;
		
	}

}
