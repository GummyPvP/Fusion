package fusion.events.cmds;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fusion.events.Event;
import fusion.events.EventManager;
import fusion.events.utils.EventState;
import fusion.utils.ItemBuilder;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

/**
	 * 
	 * Created on Dec 28, 2016 by Jeremy Gooch.
	 * 
	 */

public class EventJoin {
	
	// TODO: Make a more efficient and automatic method
	
	@Command(name = "event.join", description = "Allows you to join an Event", usage = "/event join", inGameOnly = true)
	public void eventJoinCommand(CommandArgs args) {
		
		Player player = args.getPlayer();
		
		// load events into gui
		
		Inventory inv = Bukkit.createInventory(player, InventoryType.HOPPER, ChatColor.YELLOW + "Events");
		
		ItemStack noEventItem = new ItemBuilder(Material.BARRIER).name("&cWaiting for event...").lore("&bNo event is currently running").build();
		
		Event eventOne = get(EventManager.get().getActiveEvents(), 0);
		Event eventTwo = get(EventManager.get().getActiveEvents(), 1);
		
		ItemStack eventOneItem = (eventOne == null ? noEventItem.clone() : 
			new ItemBuilder(eventOne.getItemGUI())
			.name("&eEvent #1 &7(" + getTitleAccessString(eventOne) + "&7)")
			.lore("&aType: &b" + eventOne.getType().toString())
			.lore("&aMap: &b" + eventOne.getArena().getName())
			.lore("&aPlayers: &b" + eventOne.getAmountOfPlayers() + "&7/&b" + eventOne.getMaxPlayers())
			.lore("&aState: &b" + eventOne.getState().toString().replaceAll("_", " "))
			.build());
		
		ItemStack eventTwoItem = (eventTwo == null ? noEventItem.clone() : 
			new ItemBuilder(eventTwo.getItemGUI())
			.name("&eEvent #2 &7(" + getTitleAccessString(eventTwo) + "&7)")
			.lore("&aType: &b" + eventTwo.getType().toString())
			.lore("&aMap: &b" + eventTwo.getArena().getName())
			.lore("&aPlayers: &b" + eventTwo.getAmountOfPlayers() + "&7/&b" + eventTwo.getMaxPlayers())
			.lore("&aState: &b" + eventTwo.getState().toString().replaceAll("_", " "))
			.build());
		
		inv.setItem(1, eventOneItem);
		inv.setItem(3, eventTwoItem);
		
		player.openInventory(inv);
		
	}
	
	private String getTitleAccessString(Event event) {
		
		if (event.getAmountOfPlayers() >= event.getMaxPlayers() || event.getState() == EventState.FINISHED || event.getState() == EventState.RUNNING) return "&cNot Joinable";
		
		return "&aJoinable";
		
	}
	
	private <E> E get(List<E> list, int index) {
		
		
		E object = null;
		
		try {
			
			object =  list.get(index);
			
		} catch (IndexOutOfBoundsException e) {
			
			object = null;
			
		}
		
		return object;
		
	}

}
