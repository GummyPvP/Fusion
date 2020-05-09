package fusion.kits.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fusion.kits.utils.KitManager;
import fusion.utils.chat.Chat;

public class StalkerEvent implements Listener {
	
	private Map<String, Boolean> inOpen = new HashMap<String, Boolean>();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		if (!KitManager.getInstance().hasRequiredKit(player, KitManager.getInstance().valueOf("Stalker")))
			return;
		
		if (player.getLocation().getBlock().getLightFromSky() >= 8) {
			
			if (!inOpen.containsKey(player.getName())) {
				inOpen.put(player.getName(), true);
			}
			
			if (!inOpen.get(player.getName())) {
				player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				player.removePotionEffect(PotionEffectType.SPEED);
				player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				Chat.getInstance().messagePlayer(player, "&cYou're in the open!");
				inOpen.put(player.getName(), true);
			}
			
			return;
		}
		
		if (!inOpen.containsKey(player.getName())) {
			inOpen.put(player.getName(), false);
		}
		
		if (inOpen.get(player.getName())) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
			
			Chat.getInstance().messagePlayer(player, "&cYou've blended into the shadows... you feel stronger");
			
			inOpen.put(player.getName(), false);
		}
		
	}

}
