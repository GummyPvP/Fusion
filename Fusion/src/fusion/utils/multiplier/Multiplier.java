package fusion.utils.multiplier;

import org.bukkit.ChatColor;

public enum Multiplier {

	NONE("None", 1), SLIME(ChatColor.GREEN + "Slime", MultiplierManager.slimeMultiplier), HARIBO(
			ChatColor.BLUE + "Haribo", MultiplierManager.hariboMultiplier), GUMMY(ChatColor.RED + "Gummy", MultiplierManager.gummyMultiplier);

	public String name;
	public double i;

	Multiplier(String name, double i) {
		this.name = name;
		this.i = i;
	}
}
