package fusion.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fusion.main.Fusion;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import klap.utils.Chat;
import mpermissions.utils.permissions.Rank;

public class FreeKitFriday {

	int taskid = -1;
	
	@Command(name = "freekitfriday", description = "FREE KITS HELL YEAH!.", usage = "/freekitfriday", aliases = {
			"fff" }, rank = Rank.ADMIN)
	public void candyEco(CommandArgs args) {

		if (Fusion.getInstance().freekitfriday) {

			Fusion.getInstance().freekitfriday = false;
			Chat.getInstance().messagePlayer(args.getSender(), "&cFree Kit Friday disabled.");

			Bukkit.getServer().getScheduler().cancelTask(taskid);
			taskid = -1;

		} else {

			Fusion.getInstance().freekitfriday = true;
			Chat.getInstance().messagePlayer(args.getSender(), "&aFree Kit Friday enabled.");

			int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Fusion.getInstance(), new Runnable() {
				public void run() {
					
					for (Player online : Bukkit.getOnlinePlayers()) {
						
						Chat.getInstance().messagePlayer(online, "&6It's Friday - all kits can be used for free today!");
						
						
					}
					
				}
			}, 0L, 20 * 60);

			taskid = id;

		}

	}

}
