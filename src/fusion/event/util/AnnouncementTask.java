package fusion.event.util;

import org.bukkit.scheduler.BukkitRunnable;

import fusion.utils.chat.Chat;

public class AnnouncementTask extends BukkitRunnable {
	
	int timer = 10;
	
	FusionEvent event;
	
	public AnnouncementTask(FusionEvent event) {
		this.event = event;
	}
	
	@Override
	public void run() {
		timer--;
		
		if (timer == 0) {
			
			cancel();
			
			if (event.getPlayers().size() < event.getMinimumPlayerCount()) {
				Chat.getInstance().broadcastMessage("&7[&eEvent&7] &3" + event.getName() + " &adidn't have enough players to start! Event over.");
				event.end();
				return;
			}
			
			event.start();
			
			return;
		}
		
		if (timer == 30 || timer == 15 || timer == 3 || timer == 2 || timer == 1) {
			Chat.getInstance().broadcastMessage("&7[&eEvent&7] &3" + event.getName() + " &ais starting in &e" + timer + " seconds&a! Use /event join to participate!");
		}
		
	}
	
}