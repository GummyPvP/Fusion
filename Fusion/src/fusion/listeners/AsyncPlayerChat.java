package fusion.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fusion.teams.utils.Team;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import net.md_5.bungee.api.ChatColor;

public class AsyncPlayerChat implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {

		mKitUser user = mKitUser.getInstance(event.getPlayer());

		Team team = user.getTeam();

		if (team == null) return;

		if (team.getChat().contains(event.getPlayer().getUniqueId())) {
			
			event.setCancelled(true);
			
			team.messageMembers("&a&lTeamChat " + Chat.CHAT_PREFIX + event.getPlayer().getDisplayName() + " " + Chat.CHAT_PREFIX + "&b" + event.getMessage());

			return;
		}
		
		String currentformat = event.getFormat();

		String newformat = ChatColor.translateAlternateColorCodes('&', "&8»&a" + team.getName() + "&8» ")
				+ currentformat;

		event.setFormat(newformat);


	}

}
