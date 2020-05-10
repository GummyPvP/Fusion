package fusion.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fusion.main.Fusion;
import fusion.utils.chat.Chat;

public class PlayerInteractSign implements Listener {
	
	@EventHandler
	public void onPlayerInteractSign(PlayerInteractEvent event) {
		
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		if (event.getClickedBlock().getType() == Material.OAK_SIGN || event.getClickedBlock().getType() == Material.OAK_WALL_SIGN) {
			
			BlockState state = event.getClickedBlock().getState();
			Sign sign = (Sign) state;
			
			if (!sign.getLine(0).equalsIgnoreCase("[Kit]")) return;
			
			String kitName = sign.getLine(1);
			
			if (Fusion.getInstance().getEventModeHandler().getCustomKit(kitName) == null) return;
			
			Fusion.getInstance().getEventModeHandler().applyCustomKit(event.getPlayer(), kitName);
			
			Chat.getInstance().messagePlayer(event.getPlayer(), "&eKit &a" + kitName + " &ehas been applied to you");
			
		}
		
	}

}
