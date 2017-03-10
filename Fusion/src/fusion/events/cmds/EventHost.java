package fusion.events.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Created on Jan 1, 2017 by Jeremy Gooch.
	 * 
	 */

public class EventHost {
	
	@Command(name = "event.host", description = "Allows you to host events", usage = "/event host", rank = Rank.SLIME)
	public void eventHostCommand(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		Inventory inv = Bukkit.createInventory(player, InventoryType.DROPPER, ChatColor.YELLOW + "Host an Event");
		
		ItemStack lmsItem = 
				new ItemBuilder(Material.DIAMOND_SWORD)
				.name("&e&lLMS")
				.lore("&aAmount of players required: &e")
				.lore("&bPlayers fight to the death,")
				.lore("the last man standing wins the event!")
				.build();
		
	}

}
